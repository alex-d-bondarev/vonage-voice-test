package org.test.voice;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.SmsSubmissionResponse;
import com.nexmo.client.sms.SmsSubmissionResponseMessage;
import com.nexmo.client.sms.messages.TextMessage;
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
}
