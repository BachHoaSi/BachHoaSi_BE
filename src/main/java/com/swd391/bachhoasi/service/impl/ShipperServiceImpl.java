package com.swd391.bachhoasi.service.impl;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Properties;

import com.swd391.bachhoasi.model.dto.request.AdminRequest;
import com.swd391.bachhoasi.model.dto.request.ShipperRequest;
import com.swd391.bachhoasi.model.dto.response.AdminResponse;
import com.swd391.bachhoasi.model.entity.Admin;
import com.swd391.bachhoasi.model.exception.ActionFailedException;
import com.swd391.bachhoasi.model.exception.ValidationFailedException;
import com.swd391.bachhoasi.util.AuthUtils;
import com.swd391.bachhoasi.util.BaseUtils;
import com.swd391.bachhoasi.util.PasswordGenerator;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.swd391.bachhoasi.model.dto.request.SearchRequestParamsDto;
import com.swd391.bachhoasi.model.dto.response.PaginationResponse;
import com.swd391.bachhoasi.model.dto.response.ShipperResponseDto;
import com.swd391.bachhoasi.model.entity.Shipper;
import com.swd391.bachhoasi.model.exception.NotFoundException;
import com.swd391.bachhoasi.repository.ShipperRepository;
import com.swd391.bachhoasi.service.ShipperService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipperServiceImpl implements ShipperService {

    private final ShipperRepository shipperRepository;
    private final AuthUtils authUtils;
    private final PasswordEncoder passwordEncoder;

    @Value("${mail.username}")
    private String email;
    @Value("${mail.password}")
    private String pass;
    @Override
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587); // Default SMTP port for TLS
        mailSender.setUsername(email);
        mailSender.setPassword(pass);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }

    @Override
    public PaginationResponse<ShipperResponseDto> getAllShipper(SearchRequestParamsDto search) {
        var data = shipperRepository.searchAnyByParameter(search.search(), search.pagination())
        .map(item -> ShipperResponseDto.builder()
                        .id(item.getId())
                        .name(item.getName())
                        .phone(item.getPhone())
                        .email(item.getEmail())
                        .status(item.getStatus())
                        .shippingStatus(item.getShippingStatus())
                        .licenseNumber(item.getLicenseNumber())
                        .licenseIssueDate(item.getLicenseIssueDate())
                        .idCardNumber(item.getIdCardNumber())
                        .idCardIssuePlace(item.getIdCardIssuePlace())
                        .idCardIssueDate(item.getIdCardIssueDate())
                        .vehicleType(item.getVehicleType())
                        .createdDate(item.getCreatedDate())
                        .updatedDate(item.getUpdatedDate())
                        .isActive(item.getIsActive())
                        .isLocked(item.getIsLocked())
                        .build());
        return new PaginationResponse<>(data);
    }

	@Override
	public ShipperResponseDto getShipperDetail(BigDecimal id) {
		Shipper item = shipperRepository.findById(id).orElseThrow(() 
        -> new NotFoundException(String.format("Not found shipper with id: %s", id.toString())));
        return ShipperResponseDto.builder()
        .id(item.getId())
        .name(item.getName())
        .phone(item.getPhone())
        .email(item.getEmail())
        .status(item.getStatus())
        .shippingStatus(item.getShippingStatus())
        .licenseNumber(item.getLicenseNumber())
        .licenseIssueDate(item.getLicenseIssueDate())
        .idCardNumber(item.getIdCardNumber())
        .idCardIssuePlace(item.getIdCardIssuePlace())
        .idCardIssueDate(item.getIdCardIssueDate())
        .vehicleType(item.getVehicleType())
        .createdDate(item.getCreatedDate())
        .updatedDate(item.getUpdatedDate())
        .isActive(item.getIsActive())
        .isLocked(item.getIsLocked())
        .build();
	}

    @Override
    public ShipperResponseDto getShipperWithLeastOrders() {
        List<Shipper> shippers = shipperRepository.findShipperWithLeastOrders();
        if (shippers.isEmpty()) {
            throw new NotFoundException("No shipper found with least orders");
        }
        Shipper item = shippers.get(0);
        return ShipperResponseDto.builder()
                .id(item.getId())
                .name(item.getName())
                .phone(item.getPhone())
                .email(item.getEmail())
                .status(item.getStatus())
                .shippingStatus(item.getShippingStatus())
                .licenseNumber(item.getLicenseNumber())
                .licenseIssueDate(item.getLicenseIssueDate())
                .idCardNumber(item.getIdCardNumber())
                .idCardIssuePlace(item.getIdCardIssuePlace())
                .idCardIssueDate(item.getIdCardIssueDate())
                .vehicleType(item.getVehicleType())
                .createdDate(item.getCreatedDate())
                .updatedDate(item.getUpdatedDate())
                .isActive(item.getIsActive())
                .isLocked(item.getIsLocked())
                .build();
    }

    @Override
    public ShipperResponseDto resetPassword(BigDecimal id) throws MessagingException {


            var loginUser = authUtils.getAdminUserFromAuthentication();
            if (loginUser == null)
                throw new ActionFailedException(String.format("Login user is null"));
            JavaMailSender javaMailSender = getJavaMailSender();
            MimeMessage msg = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());

            var shipper = shipperRepository.findById(id);
            if (shipper.isEmpty()) {
                throw new NotFoundException(String.format("Not found shipper with id: %s", id.toString()));
            }
            helper.setFrom(email);
            helper.setTo(shipper.get().getEmail());
            helper.setSubject("SEND RESET PASSWORD");
            String password = BaseUtils.generatePassword(12);
            helper.setText(password);
            javaMailSender.send(msg);
            /*passwordEncoder.encode(password);*/
            var hashPass = passwordEncoder.encode(password);
            Shipper shipperEntity = shipper.get();
            shipperEntity.setHashPassword(hashPass);
            try {
                var dbResult = shipperRepository.save(shipperEntity);
                return getShipperDetail(dbResult.getId());
            }catch (Exception ex) {
                throw new ActionFailedException(
                        String.format("Something happen when reset password: %s", ex.getMessage()));
            }

    }

    @Override
    public ShipperResponseDto registerNewShipper(ShipperRequest shipperRequest) throws MessagingException {
        Shipper shipper = Shipper.builder()
                .name(shipperRequest.getName())
                .phone(shipperRequest.getPhone())
                .licenseNumber(shipperRequest.getLicenseNumber())
                .licenseIssueDate(shipperRequest.getLicenseIssueDate())
                .idCardNumber(shipperRequest.getIdCardNumber())
                .idCardIssueDate(shipperRequest.getIdCardIssueDate())
                .idCardIssuePlace(shipperRequest.getIdCardIssuePlace())
                .vehicleType(shipperRequest.getVehicleType())
                .isActive(true)
                .isLocked(false)
                .build();;
        shipper.setEmail(shipperRequest.getEmail());
        JavaMailSender javaMailSender = getJavaMailSender();
        MimeMessage msg = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(msg, true, StandardCharsets.UTF_8.name());
        String password = BaseUtils.generatePassword(12);

        helper.setFrom(email);
        helper.setTo(shipperRequest.getEmail());
        helper.setSubject("SEND RESET PASSWORD");
        helper.setText(password);
        javaMailSender.send(msg);
        var hashPass = passwordEncoder.encode(password);
        shipper.setHashPassword(hashPass);
        try {
            var dbResult = shipperRepository.save(shipper);
            return convertToDto(dbResult);
        } catch (Exception ex) {
            throw new ActionFailedException(
                    String.format("Something happen when adding new shipper to system: %s", ex.getMessage()));
        }
    }

    @Override
    public ShipperResponseDto updateUser(BigDecimal id, ShipperRequest shipperRequest) {
        Shipper userDb = shipperRepository.findById(id).orElseThrow(() -> new NotFoundException(
                String.format("Can't found shipper with id: %s", id.toString())));

        Shipper updateEntity = addUpdateFieldToAdminEntity(shipperRequest, userDb);
        try {
            updateEntity = shipperRepository.save(updateEntity);
            return convertToDto(updateEntity);
        } catch (Exception ex) {
            throw new ActionFailedException(
                    String.format("Username duplicated or something else error: %s", ex.getMessage()));
        }
    }

    private ShipperResponseDto convertToDto(Shipper item) {
        return ShipperResponseDto.builder()
                .id(item.getId())
                .email(item.getEmail())
                .name(item.getName())
                .isActive(item.getIsActive())
                .isLocked(item.getIsLocked())
                .build();
    }

    private Shipper addUpdateFieldToAdminEntity(ShipperRequest request, Shipper shipperEntity) {
        if (shipperEntity == null || request == null) {
            throw new ValidationFailedException(
                    "Request or Admin entity is null on function::addUpdateFieldToAdminEntity");
        }
        shipperEntity.setName(request.getName());
        shipperEntity.setPhone(request.getPhone());
        shipperEntity.setLicenseNumber(request.getLicenseNumber());
        shipperEntity.setLicenseIssueDate(request.getLicenseIssueDate());
        shipperEntity.setIdCardNumber(request.getIdCardNumber());
        shipperEntity.setIdCardIssuePlace(request.getIdCardIssuePlace());
        shipperEntity.setLicenseIssueDate(request.getLicenseIssueDate());
        shipperEntity.setVehicleType(request.getVehicleType());

        return shipperEntity;
    }
}
