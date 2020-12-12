package com.service.message.service.sms;

import com.service.common.helpers.RandomCode;
import com.service.message.controller.requests.SmsRequest;
import com.service.message.exceptions.SmsException;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

@Service
public class SendSmsService {
    private static final String smsApiUrl = "https://api.smsdev.com.br/v1/send";

    private static final int smsType = 9;

    private static final String apiKey =
            "FCSETRIF7T8XUWVGDH9QWQDH82P8OSFP3GDH3N9GQW6Y4WL94LDO5TWNFIMHKY5A9LEYJ5X4EGP1MYQCO3WTLCDIY0ZNLER0ILXO1IEI22PRRN03LJB5AIT8LEM5KO5W";

    private HttpHeaders httpHeaders = new HttpHeaders();

    public void sendSms(SmsRequest smsRequest) throws SmsException, IOException {
        RandomCode randomCode = new RandomCode();
        String linkCode = randomCode.nextString();
        String link = smsRequest.isReceiverExists() ?
                "http://localhost:3000/convites-herdeiro"
                : "http://localhost:3000/registro?code=" + linkCode + "&invite=" + smsRequest.getInviteId();

        String message = "Convite da herança digital de " + smsRequest.getOwnerName() + "." +
                "\\nMais detalhes no link.\\nAt, equipe Herança Digital.\\n\\n" + link;

        URL url = new URL(smsApiUrl);
        URLConnection con = url.openConnection();
        HttpURLConnection http = (HttpURLConnection)con;
        http.setRequestMethod("POST");
        http.setDoOutput(true);

        String json = "{\"type\":\"" + smsType + "\",\"key\":\"" + apiKey + "\",\"msg\":\"" + message + "\",\"number\":\"" + smsRequest.getPhone() + "\"}";
        byte[] output = json.getBytes(StandardCharsets.UTF_8);
        int length = output.length;

        http.setFixedLengthStreamingMode(length);
        http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        http.connect();

        try(OutputStream os = http.getOutputStream()) {
            os.write(output);
        }
    }

}
