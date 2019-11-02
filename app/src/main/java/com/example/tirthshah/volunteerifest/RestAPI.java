package com.example.tirthshah.volunteerifest;

/**
 * Created by tirthshah on 04/08/16.
 */

        import android.content.Context;
        import android.os.StrictMode;
        import android.util.Base64;
        import android.util.Log;

        import java.io.BufferedInputStream;
        import java.io.BufferedReader;
        import java.io.InputStream;
        import java.io.InputStreamReader;
        import java.net.HttpURLConnection;
        import java.net.URL;

/**
 * Created by Nishit I5547 on 26-01-2016.
 */
public class RestAPI  {

    private static final String LOGTAG = "RestAPI";
    public static final String REST_URL_LOGIN = "https://webmail.daiict.ac.in/service/home/~/inbox.rss?limit=1";
    public static final String REST_URL_INBOX = "https://webmail.daiict.ac.in/home/~/inbox.json";
    public static final String REST_URL_VIEW_WEBMAIL = "https://webmail.daiict.ac.in/home/~/?id=";
    public static final String REST_URL_SENT = "https://webmail.daiict.ac.in/home/~/sent.json";
    public static final String REST_URL_TRASH = "https://webmail.daiict.ac.in/home/~/trash.json";

    private static final int TIME_OUT = 10 * 1000;

    private String username, password;
    private Context context;

    private boolean important = false;

    //private ArrayList<EmailMessage> allNewEmails = new ArrayList<>();

    public RestAPI(String username, String password, Context context) {
        this.username = username;
        this.password = password;
        this.context = context;

//            Toast.makeText(context,""+username,Toast.LENGTH_LONG).show();
        System.out.println("Userqwqwsqdwqqdw"+username);
        System.out.println("pass"+password);
    }





    public boolean logIn() {

        return makeLoginRequest();

    }

    /* public boolean refresh(String TYPE) {
         if (TYPE.equals(SyncStateContract.Constants.INBOX)) {
             return makeInboxRefreshRequest();
         } else if (TYPE.equals(SyncStateContract.Constants.SENT)) {
             return makeSentRefreshRequest();
         } else if (TYPE.equals(SyncStateContract.Constants.TRASH)) {
             return makeTrashRefreshRequest();
         }
         return false;
     }

     public EmailMessage fetchEmailContent(EmailMessage emailMessage) {
         return makeFetchRequest(emailMessage);
     }
*/
    private boolean makeLoginRequest() {

        System.out.println("This is above try");
        try {
            if (android.os.Build.VERSION.SDK_INT > 9) {
                StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
            }
            URL url = new URL(REST_URL_LOGIN);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            String userPassword = username + ":" + password;
            String encoding = Base64.encodeToString(userPassword.getBytes(), Base64.DEFAULT);
            conn.setRequestProperty("Authorization", "Basic " + encoding);
            conn.setReadTimeout(TIME_OUT);

            conn.connect();
            System.out.println("This is below try");

            // Toast.makeText(,"1",Toast.LENGTH_SHORT).show();
            Log.d(LOGTAG, "Response Code: " + conn.getResponseCode());
            //   System.out.println("rajna"+conn.getContentEncoding());
            if (conn.getResponseCode()==200) {
                //System.out.println("This is abovexsddjhdsd try");
                //   Toast.makeText(context,"2",Toast.LENGTH_SHORT).show();

                Log.d(LOGTAG, "Authenticated User Successfully");
                System.out.println("Login successfull");
                InputStream in = new BufferedInputStream(conn.getInputStream());
                BufferedReader r = new BufferedReader(new InputStreamReader(in));
                StringBuilder total = new StringBuilder();
                String line;
                while ((line = r.readLine()) != null) {
                    total.append(line);
                }
                Log.d(LOGTAG, "" + total.toString());
                in.close();
                return true;
            } else {
                Log.d(LOGTAG, "Unable to Authenticate User");
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }



}