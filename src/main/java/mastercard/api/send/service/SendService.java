package mastercard.api.send.service;

import org.json.JSONException;
import org.openapitools.client.model.PaymentTransferWrapper;
import org.openapitools.client.model.TransferWrapper;

import mastercard.api.send.model.PaymentRequestWrapper;

public interface SendService {

	/**
	 * Takes a PaymentRequestWrapper, and uses it to make an API call which will create a payment.
	 * Uses unwrapRequest to get the data in the form needed.
	 * 
	 * @param paymentRequestWrapper PaymentRequestWrapper instance, that contains all data needed to construct request
	 * @return Instance of PaymentResponse if the call was made successfully, or null otherwise
	 * @throws JSONException 
	 */
	TransferWrapper makeCall(PaymentRequestWrapper paymentRequestWrapper) throws JSONException;

	/**
	 * Returns most recent PaymentRequest
	 * @return paymentRequest
	 */
	PaymentTransferWrapper getPaymentRequest();

	/**
	 * Returns most recent error response body
	 * @return error response
	 */
	String getError();

	/**
	 * Returns most recent error message
	 * @return error message
	 */
	String getErrorMessage();

	/**
	 * Sets the errorMessage. Mostly used to reset the error message after it is used in the controller
	 * @param newErrorMessage string containing new error message
	 */
	void setErrorMessage(String newErrorMessage);

}