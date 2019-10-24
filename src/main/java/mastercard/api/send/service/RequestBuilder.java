package mastercard.api.send.service;

import java.util.Random;

import org.openapitools.client.model.Address;
import org.openapitools.client.model.PaymentTransfer;
import org.openapitools.client.model.PaymentTransferWrapper;
import org.openapitools.client.model.Sender;

import mastercard.api.send.model.PaymentRequestWrapper;


/**
 * Utility class for building requests
 */
public class RequestBuilder {
	
	/**
	 * Creates and returns Recipient Account URI 
	 * @param paymentRequestWrapper
	 * @return {@link String} with Recipient Account URI value
	 */
	private static String createRecipientAccountURI(PaymentRequestWrapper paymentRequestWrapper) {
		return "pan:" + paymentRequestWrapper.getRecipientUriIdentifier() + ";exp=" + paymentRequestWrapper.getRecipientUriExpYear()
                + "-" + paymentRequestWrapper.getRecipientUriExpMonth() + ";cvc=" + paymentRequestWrapper.getRecipientUriCvc();
	}

	/**
	 * Creates and returns Sender Account URI 
	 * @param paymentRequestWrapper
	 * @return {@link String} with Sender Account URI value
	 */
	private static String createSenderAccountURI(PaymentRequestWrapper paymentRequestWrapper) {
		return "pan:" + paymentRequestWrapper.getSenderUriIdentifier() + ";exp=" + paymentRequestWrapper.getSenderUriExpYear()
                + "-" + paymentRequestWrapper.getSenderUriExpMonth() + ";cvc=" + paymentRequestWrapper.getSenderUriCvc();
	}

	/**
	 * Generate a unique payment reference for a transaction, note this is a quick sample developed for demo code and should not be used in real application
	 * @return {@link String} with unique number
	 */
	private static String generatePaymentReference() {
		Random random = new Random();
        long n = (long) (100000000000000L + random.nextFloat() * 900000000000000L);
        String paymentReference = n + "";   //Quickly coerce our long into a string
		return paymentReference;
	}


    /**
     * Creates an instance of PaymentRequestWrapper with pre-populated fields to
     * allow for fast form submissions.
     * @return Instance of PaymentRequestWrapper class
     */
    public static PaymentRequestWrapper createPrefilledWrapper() {
        PaymentRequestWrapper paymentRequestWrapper = new PaymentRequestWrapper();
        paymentRequestWrapper.setSenderFirstName("John");
        paymentRequestWrapper.setSenderLastName("Wrangler");
        paymentRequestWrapper.setSenderStreet("114 Wacker Ave");
        paymentRequestWrapper.setSenderCity("Chicago");
        paymentRequestWrapper.setSenderPostalCode("22245");
        paymentRequestWrapper.setSenderCountrySubdivision("IL");
        paymentRequestWrapper.setSenderCountry("USA");
        paymentRequestWrapper.setSenderUriScheme("PAN");
        paymentRequestWrapper.setSenderUriIdentifier("5432123456789012");
        paymentRequestWrapper.setSenderUriExpYear("2050");
        paymentRequestWrapper.setSenderUriExpMonth("02");
        paymentRequestWrapper.setSenderUriCvc("123");
        paymentRequestWrapper.setRecipientStreet("2200 Mastercard Blvd");
        paymentRequestWrapper.setRecipientCity("Cape Girardeau");
        paymentRequestWrapper.setRecipientPostalCode("23232");
        paymentRequestWrapper.setRecipientCountrySubdivision("MO");
        paymentRequestWrapper.setRecipientCountry("USA");
        paymentRequestWrapper.setRecipientFirstName("Jane");
        paymentRequestWrapper.setRecipientLastName("Juniper");
        paymentRequestWrapper.setRecipientUriScheme("PAN");
        paymentRequestWrapper.setRecipientUriIdentifier("4024140000000065");
        paymentRequestWrapper.setRecipientUriExpYear("2050");
        paymentRequestWrapper.setRecipientUriExpMonth("02");
        paymentRequestWrapper.setRecipientUriCvc("123");
        paymentRequestWrapper.setRecipientNameOnAccount("Jane Juniper");
        paymentRequestWrapper.setTransferAcceptorId("456487898368");
        paymentRequestWrapper.setTransferAcceptorTerminalId("1367-hgf");
        paymentRequestWrapper.setTransferAcceptorName("transferacceptr");
        paymentRequestWrapper.setTransferAcceptorPaymentFacilitatorId("123");
        paymentRequestWrapper.setTransferAcceptorSubMerchantId("223");
        paymentRequestWrapper.setTransferAcceptorMastercardAssignedId("12A346");
        paymentRequestWrapper.setTransferAcceptorStreet("1400 Michigan Ave");
        paymentRequestWrapper.setTransferAcceptorCity("Port Richey");
        paymentRequestWrapper.setTransferAcceptorPostalCode("12345");
        paymentRequestWrapper.setTransferAcceptorCountrySubdivision("FL");
        paymentRequestWrapper.setTransferAcceptorCountry("USA");
        paymentRequestWrapper.setAcquiringICA("1234");
        paymentRequestWrapper.setAcquiringCountry("USA");
        paymentRequestWrapper.setAcquiringBIN("123456");
        paymentRequestWrapper.setAcquiringProcessorId("1234567898");
        paymentRequestWrapper.setAcquiringIdentificationCode("12346");
        paymentRequestWrapper.setFundingSource("CREDIT");
        paymentRequestWrapper.setMerchantCategoryCode("4121");
        paymentRequestWrapper.setPaymentType("P2P");
        paymentRequestWrapper.setAmount("1000");
        paymentRequestWrapper.setCurrency("USD");
        return paymentRequestWrapper;
    }
    /**
     * Create API Input {@link PaymentTransferWrapper} object using {@link PaymentRequestWrapper}
     * 
     * @param paymentRequestWrapper
     * @return {@link PaymentTransferWrapper}
     */
    static PaymentTransferWrapper createPaymentTransferWrapper(PaymentRequestWrapper paymentRequestWrapper) {
    	PaymentTransferWrapper paymentTransferWrapper = new PaymentTransferWrapper();
    	
    	
    	
    	PaymentTransfer transfer = new PaymentTransfer();
    	
    	transfer.setAmount(paymentRequestWrapper.getAmount());
    	transfer.setCurrency(paymentRequestWrapper.getCurrency());
    	Sender sender = new Sender();
    	sender.setFirstName(paymentRequestWrapper.getSenderFirstName());
    	sender.setLastName(paymentRequestWrapper.getSenderLastName());
    	
    	Address address = new Address();
    	
    	address.setCity(paymentRequestWrapper.getSenderCity());
    	address.setLine1(paymentRequestWrapper.getSenderStreet());
    	address.setCity(paymentRequestWrapper.getSenderCity());
    	address.setCountry(paymentRequestWrapper.getSenderCountry());
    	address.setPostalCode(paymentRequestWrapper.getSenderPostalCode());
    	address.setCountrySubdivision(paymentRequestWrapper.getSenderCountrySubdivision());
		sender.setAddress(address);
    	
    	transfer.setFundingSource(paymentRequestWrapper.getFundingSource());
    	transfer.setPaymentType(paymentRequestWrapper.getPaymentType());
    	transfer.setTransferReference(generatePaymentReference());
		transfer.setSender(sender);
    	transfer.setSenderAccountUri(createSenderAccountURI(paymentRequestWrapper));
    	transfer.setRecipientAccountUri(createRecipientAccountURI(paymentRequestWrapper));
    	
    	
    	paymentTransferWrapper.setPaymentTransfer(transfer);
		return paymentTransferWrapper;
    }
}
