package org.test.voice;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.insight.AdvancedInsightResponse;
import com.nexmo.client.insight.RoamingDetails;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;
import com.nexmo.client.verify.VerifyClient;
import com.nexmo.client.verify.VerifyRequest;
import com.nexmo.client.verify.VerifyResponse;
import com.nexmo.client.verify.VerifyStatus;
import com.nexmo.client.voice.Call;
import com.nexmo.client.voice.CallEvent;
import com.nexmo.client.voice.VoiceName;
import com.nexmo.client.voice.ncco.Ncco;
import com.nexmo.client.voice.ncco.TalkAction;
import org.junit.Ignore;
import org.junit.Test;

public class TestVonageAPI {

    @Ignore
    @Test
    public void testVoice() {
        NexmoClient client = NexmoClient.builder()
                .applicationId("find your own app id")
                .privateKeyPath("find your own key")
                .build();

        Ncco ncco = new Ncco(
                TalkAction
                        .builder("This is a text-to-speech test message.")
                        .voiceName(VoiceName.JOEY)
                        .build()
        );

        String TO_NUMBER = "find your own phone";
        String FROM_NUMBER = "find your own phone";

        CallEvent result = client
                .getVoiceClient()
                .createCall(new Call(TO_NUMBER, FROM_NUMBER, ncco));

        System.out.println(result.getConversationUuid());
    }

    @Ignore
    @Test
    public void testSMS() {
        NexmoClient client = new NexmoClient.Builder()
                .apiKey("find your own key")
                .apiSecret("find your own secret")
                .build();

        String messageText = "Hello from Vonage SMS API";
        TextMessage message = new TextMessage("Vonage APIs", "find your own phone", messageText);

        SmsSubmissionResponse response = client.getSmsClient().submitMessage(message);

        for (SmsSubmissionResponseMessage responseMessage : response.getMessages()) {
            System.out.println(responseMessage);
        }
    }

    @Ignore
    @Test
    public void testVerify() {
        boolean cancelRequest = true;

        NexmoClient client = new NexmoClient.Builder() // "new" was missing
                .apiKey("find your own key")
                .apiSecret("find your own secret")
                .build();
        VerifyClient verifyClient = client.getVerifyClient();

        if (!cancelRequest) {
            // "deprecated" code in getting started guide?
            // What should I use instead?
            // Unclear from code source.
            VerifyRequest request = new VerifyRequest("find your own phone", "Vonage");
            request.setLength(4);

            VerifyResponse response = verifyClient.verify(request);

            if (response.getStatus() == VerifyStatus.OK) {
                System.out.printf("RequestID: %s", response.getRequestId());
                // RequestID: 9f88b52274de4c4fb14345450612ee4d
                // Why did this pass?
                // I got the code "6091" only 2 minutes after this test was executed.
                // It should have failed.
                // In 2 minutes I got the next code 0_o "4144"
            } else {
                System.out.printf("ERROR! %s: %s",
                        response.getStatus(),
                        response.getErrorText()
                );
            }
        }

        verifyClient.cancelVerification("9f88b52274de4c4fb14345450612ee4d");
        // Status 19: Verification request  ['9f88b52274de4c4fb14345450612ee4d'] can't be cancelled now.
        // Too many attempts to re-deliver have already been made.
        System.out.println("Verification cancelled.");
    }

    @Ignore
    @Test
    public void testNumberInsight() {
        NexmoClient client = new NexmoClient.Builder() // "new" was missing
                .apiKey("find your own key")
                .apiSecret("find your own secret")
                .build();

        AdvancedInsightResponse response = client.getInsightClient()
                .getAdvancedNumberInsight("find your own phone");

        System.out.println("BASIC INFO:");
        System.out.println("International format: " + response.getInternationalFormatNumber());
        System.out.println("National format: " + response.getNationalFormatNumber());
        System.out.println("Country: " + response.getCountryName() + " (" + response.getCountryCodeIso3() + ", +"+ response.getCountryPrefix() + ")");
        System.out.println();
        System.out.println("STANDARD INFO:");
        System.out.println("Current carrier: " + response.getCurrentCarrier().getName());
        System.out.println("Original carrier: " + response.getOriginalCarrier().getName());

        System.out.println();
        System.out.println("ADVANCED INFO:");
        System.out.println("Validity: " + response.getValidNumber());
        System.out.println("Reachability: " + response.getReachability());
        System.out.println("Ported status: " + response.getPorted());

        RoamingDetails roaming = response.getRoaming();
        if (roaming == null) {
            System.out.println("- No Roaming Info -");
        } else {
            System.out.println("Roaming status: " + roaming.getStatus());
            if (response.getRoaming().getStatus() == RoamingDetails.RoamingStatus.ROAMING) {
                System.out.print("    Currently roaming in: " + roaming.getRoamingCountryCode());
                System.out.println(" on the network " + roaming.getRoamingNetworkName());
            }
        }
    }
}
