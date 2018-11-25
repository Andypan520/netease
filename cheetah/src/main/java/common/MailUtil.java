package common;

import common.meta.RunDetailReport;
import org.apache.commons.mail.*;
import org.apache.commons.mail.resolver.DataSourceUrlResolver;

import java.net.URL;
import java.util.List;

/**
 * Created by pandechuan on 2018/11/02.
 */
public class MailUtil {

    private static final String SMTP_163_HOST = "smtp.163.com";
    private static final String EMAIL_ACCOUNT = "HZgonghuishenhe@163.com";
    private static final String EMAIL_AUTH_CODE = "092774qiaoA";
    private static final String LINE_BREAK = "<br/>";
    private static final String T0 = "andypan520@yeah.net";


    /**
     * Andypan 御用
     *
     * @param runDetailReport
     */
    public static void sendRunReport(RunDetailReport runDetailReport) throws Exception {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(SMTP_163_HOST);
        email.setAuthentication(EMAIL_ACCOUNT, EMAIL_AUTH_CODE);
        email.setFrom(EMAIL_ACCOUNT, "飞鸽传书", "UTF-8");
        //email.addTo("pandechuan@corp.netease.com,17791706810@163.com");
        email.addTo("caixuedan@corp.netease.com","pandechuan@corp.netease.com");
       // email.addTo("17791706810@163.com");

        email.setSubject(runDetailReport.getSubject());

        String msg = "<h3>" + "Subject" + "</h3>"
                + LINE_BREAK +
                //"<span>" + runDetailReport.getSubject() + "</span>"
                runDetailReport.getSubject()
                + LINE_BREAK +
                "<h3>" + "Method" + "</h3>"
                + LINE_BREAK +
                runDetailReport.getMethod()
                + "<h3>" + "Info" + "</h3>"
                + LINE_BREAK +
                runDetailReport.getInfo()
                + "<h3>" + "CostTime" + "</h3>"
                + LINE_BREAK +
                runDetailReport.getCostTime()
                + "<h3>" + "SysTime" + "</h3>"
                + LINE_BREAK +
                runDetailReport.getSysTime()
                + "<h3>" + "Error" + "</h3>"
                + LINE_BREAK +
                runDetailReport.getError();
        email.setHtmlMsg(msg);
        //  email.setMsg(JsonUtils.toJson(runDetailReport));
        email.setCharset("utf-8");
        email.send();
    }

//    public static void sendTextMsg(EmailMeta emailMeta) {
//        Email email = new SimpleEmail();
//        email.setHostName(SMTP_163_HOST);
//        try {
//            email.addTo(emailMeta.getToList().toArray(new String[]{}));
//            List<String> ccList = emailMeta.getCcList();
//            if (ccList != null && ccList.size() > 0) {
//                email.addCc(emailMeta.getCcList().toArray(new String[]{}));
//            }
//            email.setAuthentication(EMAIL_ACCOUNT, EMAIL_AUTH_CODE);
//            email.setFrom(EMAIL_ACCOUNT);
//            email.setSubject(emailMeta.getSubject());
//            email.setMsg(emailMeta.getContent());
//            email.setCharset("utf-8");
//            email.send();
//        } catch (EmailException e) {
//            logger.warn("send mail error when union apply successfully", e);
//        }
//    }

//    public static void sendImageMsg(EmailMeta emailMeta) throws Exception {
//        ImageHtmlEmail email = new ImageHtmlEmail();
//        email.setHostName(SMTP_163_HOST);
//        email.setHostName(SMTP_163_HOST);
//        email.setAuthentication(EMAIL_ACCOUNT, EMAIL_AUTH_CODE);
//        email.setFrom(EMAIL_ACCOUNT, "LOOK直播公会审核", "UTF-8");
//        email.addTo(emailMeta.getToList().toArray(new String[]{}));
//        List<String> ccList = emailMeta.getCcList();
//        if (ccList != null && ccList.size() > 0) {
//            email.addCc(emailMeta.getCcList().toArray(new String[]{}));
//        }
//        email.setSubject(emailMeta.getSubject());
//
//        StringBuilder contentBuilder = new StringBuilder(emailMeta.getContent());
//
//        if (emailMeta.getTitleImageMap() != null && !emailMeta.getTitleImageMap().isEmpty()) {
//            URL url = new URL("http://p1.iplay.126.net/");
//            email.setDataSourceResolver(new DataSourceUrlResolver(url));
//            contentBuilder.append(LINE_BREAK);
//            emailMeta.getTitleImageMap().forEach((k, v) -> {
//                contentBuilder.append(LINE_BREAK)
//                        .append("<p style='text-align: center'>")
//                        .append(k)
//                        .append("</p>")
//                        .append("<img src=\"")
//                        .append(v)
//                        .append("\">")
//                        .append(LINE_BREAK);
//            });
//        }
//        email.setHtmlMsg(contentBuilder.toString());
//        email.setCharset("utf-8");
//        email.send();
//    }

    /*  public static void test() {
          // create the email message
          ImageHtmlEmail email = new ImageHtmlEmail();
          //设置发送主机的服务器地址
          email.setHostName(SMTP_163_HOST);
          email.setAuthentication(EMAIL_ACCOUNT, EMAIL_AUTH_CODE);
          try {
              URL url = new URL("http://p1.iplay.126.net/");
              email.setDataSourceResolver(new DataSourceUrlResolver(url));
              email.setFrom(EMAIL_ACCOUNT);
              email.setSubject("SUBJECT");
              String content = "========";
              String picturlContent = "<img src=\"http://p1.iplay.126.net/liveimgs_1538995780890_img_0.png\">";
              String contents = content + LINE_BREAK + picturlContent;
              email.setHtmlMsg(contents);
              // set the alternative message
              email.setTextMsg("==============");
              email.addTo(T0);
              email.send();
          } catch (MalformedURLException | EmailException e) {
              e.printStackTrace();
          }
      }
  */
//    public static void main(String[] args) {
//        sendRunReport(RunDetailReport.newBuilder()
//                .subject("你大爷还是你大爷")
//                .costTime(1300)
//                .error("jjjjjjjjjjjjjjjjjjjjjjjjj")
//                .info("uuuuuuuuuuuuuuuuuuuuu")
//                .method("LiveMailUtils.sendRunReport")
//                .sysTime(LocalDateTime.now()).
//                        build());
//    }
}
