package carson.mobi.paycel.web.websocket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

/**
 * Created by JuanIgnacio on 12/04/2016.
 */
@Controller
public class SmsService {

    @MessageMapping("/enviar" )
    public String enviar(String numero, String texto){
        return numero + texto;
    }
}
