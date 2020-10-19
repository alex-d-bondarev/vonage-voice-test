package org.test.voice;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.voice.Call;
import com.nexmo.client.voice.CallEvent;
import com.nexmo.client.voice.VoiceName;
import com.nexmo.client.voice.ncco.Ncco;
import com.nexmo.client.voice.ncco.TalkAction;
import org.junit.Test;

public class TestVonageAPI {

    @Test
    public void testVoice() {
        NexmoClient client = NexmoClient.builder()
                .applicationId("c059d7ba-cd21-4bcc-91b3-91d32eae7de7")
                .privateKeyPath("/Users/Alex/.ssh/keys/vonage/private.key")
                .build();

        Ncco ncco = new Ncco(
                TalkAction
                        .builder("This is a text-to-speech test message.")
                        .voiceName(VoiceName.JOEY)
                        .build()
        );

        String TO_NUMBER = "380931087097";
        String FROM_NUMBER = "380931087097";

        CallEvent result = client
                .getVoiceClient()
                .createCall(new Call(TO_NUMBER, FROM_NUMBER, ncco));

        System.out.println(result.getConversationUuid());
    }


}
