import java.io.InputStream;
import java.io.IOException;

public class Request {
	private InputStream input;
    private String uri;
    private String method;

    public Request(InputStream input) {
        this.input = input;
    }

    //从InputStream中读取request信息，并从request中获取uri值
    public void parse() {
        StringBuffer request = new StringBuffer(2048);
        int i;
        byte[] buffer = new byte[2048];
        try {
            i = input.read(buffer);
        } catch (IOException e) {
            e.printStackTrace();
            i = -1;
        }
        for (int j = 0; j < i; j++) {
            request.append((char) buffer[j]);
        }
        System.out.print("Requesting:\n" + request.toString());
		method = parseMethod(request.toString());
		if (method != "POST") {
			uri = parseUri(request.toString());
		}
		else {
			uri = null;
		}
    }
    
    private String parseMethod(String requestString) {
        int index;
        index = requestString.indexOf(' ');
        if (index != -1) { 
            return requestString.substring(0, index);
        }
        return null;
    }
    
    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1)
                return requestString.substring(index1 + 1, index2);
        }
        return null;
    }
        
    public String parseType() {
    	if (uri != null) {
    		int index;
    		int length = uri.length();
            index = uri.indexOf('.');
            if(index != -1) {
            	return uri.substring(index + 1, length);
            }
    	}
        return null;
    }
    
    public String getUri() {
        return uri;
    }
    
    public String getMethod() {
		return method;
	}
    
}
