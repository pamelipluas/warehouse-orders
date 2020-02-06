import java.util.ArrayList;
import java.util.List;

public class MailServiceImpl implements MailService {
    private List<Message> messages;

    public MailServiceImpl() {
        this.messages = new ArrayList<>();
    }

    public void send(Message msg) {
        this.messages.add(msg);
    }
    public int numberSent() {
        return this.messages.size();
    }
}
