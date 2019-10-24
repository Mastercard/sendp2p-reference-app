package mastercard.api.send.service;

import org.json.JSONException;
import org.json.JSONObject;
import org.openapitools.client.ApiException;
import org.openapitools.client.api.PaymentTransferApi;
import org.openapitools.client.model.PaymentTransferWrapper;
import org.openapitools.client.model.TransferWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import mastercard.api.send.model.PaymentRequestWrapper;

@Service
public class SendServiceImpl implements SendService {
	/**
	 * Partner ID injected from {@code application.properties}
	 */
	@Value("${partnerId}")
	private String partnerId;

	/**
	 * PaymentTransferApi instance 
	 */
	@Autowired
	private PaymentTransferApi paymentTransferApi;


	/**
	 * {@link PaymentRequestWrapper} instance holding recent data
	 */
	private PaymentTransferWrapper paymentTransferWrapper;

	// Most recent error response
	private String error;

	// Message to accompany most recent error response
	private String errorMessage = "";

	@Autowired
	public SendServiceImpl() {
	}

	/**
	 * 
	 * @see
	 * mastercard.api.send.service.SendServiceInterface#makeCall(mastercard.api.send
	 * .model.PaymentRequestWrapper)
	 */
	@Override
	public TransferWrapper makeCall(PaymentRequestWrapper paymentRequestWrapper) throws JSONException {

		paymentTransferWrapper = RequestBuilder.createPaymentTransferWrapper(paymentRequestWrapper);

		try {
			return paymentTransferApi.createPayment(partnerId, paymentTransferWrapper);
		} catch (ApiException e) {
			JSONObject json = new JSONObject(e.getResponseBody()).getJSONObject("Errors").getJSONArray("Error")
					.getJSONObject(0);
			errorMessage = "Error creating payment";
			error = json.toString(4);
		} 

		return null;
	}


	/**
	 * 
	 * @see mastercard.api.send.service.SendServiceInterface#getPaymentRequest()
	 */
	@Override
	public PaymentTransferWrapper getPaymentRequest() {
		return paymentTransferWrapper;
	}

	/**
	 * 
	 * @see mastercard.api.send.service.SendServiceInterface#getError()
	 */
	@Override
	public String getError() {
		return error;
	}

	/**
	 * 
	 * @see mastercard.api.send.service.SendServiceInterface#getErrorMessage()
	 */
	@Override
	public String getErrorMessage() {
		return errorMessage;
	}

	/**
	 * 
	 * @see
	 * mastercard.api.send.service.SendServiceInterface#setErrorMessage(java.lang.
	 * String)
	 */
	@Override
	public void setErrorMessage(String newErrorMessage) {
		errorMessage = newErrorMessage;
	}

}
