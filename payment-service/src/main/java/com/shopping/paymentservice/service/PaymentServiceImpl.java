package com.shopping.paymentservice.service;

import java.time.Instant;

import org.springframework.stereotype.Service;

import com.shopping.paymentservice.beans.PaymentMode;
import com.shopping.paymentservice.entity.TransactionDetails;
import com.shopping.paymentservice.exception.PaymentServiceCustomException;
import com.shopping.paymentservice.repository.TransactionDetailsRepository;
import com.shopping.paymentservice.request.PaymentRequest;
import com.shopping.paymentservice.response.PaymentResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService{

    private final TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public long doPayment(PaymentRequest paymentRequest) {

        log.info("PaymentServiceImpl | doPayment is called");

        log.info("PaymentServiceImpl | doPayment | Recording Payment Details: {}", paymentRequest);

        TransactionDetails transactionDetails
                = TransactionDetails.builder()
                .paymentDate(Instant.now())
                .paymentMode(paymentRequest.getPaymentMode().name())
                .paymentStatus("SUCCESS")
                .orderId(paymentRequest.getOrderId())
                .referenceNumber(paymentRequest.getReferenceNumber())
                .amount(paymentRequest.getAmount())
                .build();

        transactionDetails = transactionDetailsRepository.save(transactionDetails);

        log.info("Transaction Completed with Id: {}", transactionDetails.getId());

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long orderId) {

        log.info("PaymentServiceImpl | getPaymentDetailsByOrderId is called");

        log.info("PaymentServiceImpl | getPaymentDetailsByOrderId | Getting payment details for the Order Id: {}", orderId);

        TransactionDetails transactionDetails
                = transactionDetailsRepository.findByOrderId(orderId)
                .orElseThrow(() -> new PaymentServiceCustomException(
                        "TransactionDetails with given id not found",
                        "TRANSACTION_NOT_FOUND"));

        PaymentResponse paymentResponse
                = PaymentResponse.builder()
                .paymentId(transactionDetails.getId())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .paymentDate(transactionDetails.getPaymentDate())
                .orderId(transactionDetails.getOrderId())
                .status(transactionDetails.getPaymentStatus())
                .amount(transactionDetails.getAmount())
                .build();

        log.info("PaymentServiceImpl | getPaymentDetailsByOrderId | paymentResponse: {}", paymentResponse.toString());

        return paymentResponse;
    }
}
