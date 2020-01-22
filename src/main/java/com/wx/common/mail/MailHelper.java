/*
 * Created on 2005-11-14
 *
 */
package com.wx.common.mail;


import javax.mail.MessagingException;

/**
 * 邮件发送助手
 * 
 * @author 马劼
 */
public class MailHelper {

	/**
	 * 获得文本型邮件.
	 * 
	 * @param smtp
	 *          smtp地址
	 * @param un
	 *          smtp用户名
	 * @param pwd
	 *          smtp密码
	 * @return TextMail 文本型邮件.
	 */
	public static TextMail buildTextMail(String smtp, String un, String pwd) {
		return new TextMail(smtp, un, pwd);
	}
	
	/**
	 * 获得HTML型邮件.
	 * 
	 * @param smtp
	 *          smtp地址
	 * @param un
	 *          smtp用户名
	 * @param pwd
	 *          smtp密码
	 * @return TextMail 文本型邮件.
	 */
	public static HtmlMail buildHtmlMail(String smtp, String un, String pwd) {
		return new HtmlMail(smtp, un, pwd);
	}

	/**
	 * 发送邮件
	 *
	 * @param mail
	 * @throws MessagingException
	 */
	public static void sendMail(Mail mail)
	    throws MessagingException {
		mail.sendMail();
	}
	
	/**
	 * 发送邮件 带编码
	 * @author SQ
	 * @date 2015年1月22日 下午2:13:27 
	 *
	 */
	public static void sendMail(Mail mail, String charset)
		    throws MessagingException {
			mail.sendMail(charset);
	}

//	public static void main(String[] args) throws Exception{
//		// 邮件内容
//		StringBuilder content = new StringBuilder();
//		content.append("<h3>汇总报告</h3>");
//		// 发邮件
//		Mail mail = MailHelper.buildHtmlMail(, PropertyConst.mailFromAddress,
//				PropertyConst.mailPassword);
//		try {
//			// 发件地址
//			mail.setFromAddress(PropertyConst.mailFromAddress);
//			// 收件地址
//			mail.setToAddress(new String[]{"sq@kingyee.com.cn"});
//			// 标题
//			mail.setSubject("精分随访管理-汇总报告");
//			// 内容
//			mail.setContent(content.toString());
//
//			/*添加附件  fileName D://手机.ppt*/
////			mail.addFile(fileName);
//			if (PropertyConst.emailSwtich.equalsIgnoreCase("on")) {
//				// 发送邮件
//				MailHelper.sendMail(mail, "utf-8");
//			} else {
//				System.out.println("发送提醒邮件的开关已关闭！如需重新打开，请修改配置文件configConst.properties！");
//			}
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
