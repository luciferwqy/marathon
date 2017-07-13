package com.qingdao.marathon.pay.alipay.config;

import com.qingdao.marathon.util.URLUtils;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *版本：3.4
 *修改日期：2016-03-08
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 合作身份者ID，签约账号，以2088开头由16位纯数字组成的字符串，查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String partner = "2088621771197311";
	
	// 收款支付宝账号，以2088开头由16位纯数字组成的字符串，一般情况下收款账号就是签约账号
	public static String seller_id = partner;

	//商户的私钥,需要PKCS8格式，RSA公私钥生成：https://doc.open.alipay.com/doc2/detail.htm?spm=a219a.7629140.0.0.nBDxfy&treeId=58&articleId=103242&docType=1
	public static String private_key = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAJ6xiHc/WdPCrP++6UGmqxV/uGkZ23KWf7WlWds6NbG61tc4zaDtPCLO6rUELIWzc87DgriPYfdC6TGsakmJ0eQphuB7WCPrS+CZQTJ+C7Byo4d3Q0hm5I7irrjEXJdfAKqhUmdDdnC3kxp4uL8AoWmaTe3IjDij02DGZ3N0ajq/AgMBAAECgYA08JbM6TZnPW+HFcIs45P5ZRU5AqZBsNFD4Lh8OQwGkYTwYYO/psTD+/EymATeSYJdvL1UIokU+p5estz2UWLSSxxfQeuNihojRMsF19D0Yii121I4oO1yU8dfnm563sjeJGrcLFKGu2OApRsJ//NBz2Yk4CpXFrd3xSehUPaU+QJBANQbmC9jpkMfledjU+QDJzxV2rRr+1fEaD3ov3+VrPLDWwqFwfK6Mpvtjw4ldIw/BqmAWt7oIYIljO690jy+e7MCQQC/iFEuPTY4/0bm3AYTWAcn42C4fTXce2j8XLzc3iG1/9OJoF22Ay1CY9HsJ2l4/qlORFx++oZ5dPwmcx4XCc7FAkA/DXzuCXdxWMBdKp/TlkRhoTwEil0LOC2sybWznbDt8bjspdqD52oIBFSUZfoBfvLiDG/295aOc88zYWZP/GgVAkEAk9RMDCtUqYNmnxLdYZrE0yWtZlfF9aoO6xFM+BA+5rFQ/V2YVr529VIEGYiF4Mx1sqIS3jLRDfecErUxWUjJnQJAOmGae4MDD6NO1bQLfWvx/vpezQvH94n7bBzcPx/OjS+lSxwCZS9FukFYGy9vS5CS7J2fp9yieb+vuWaSKtosAQ==";
	
	// 支付宝的公钥,查看地址：https://b.alipay.com/order/pidAndKey.htm
	public static String alipay_public_key  = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCnxj/9qwVfgoUh/y2W89L6BkRAFljhNhgPdyPuBV64bfQNN1PjbCzkIM6qRdKBoLPXmKKMiFYnkd6rAoprih3/PrQEB/VsW8OoM8fxn67UDYuyBTqA23MML9q1+ilIZwBC2AQ2UBVOrFXfFl75p6/B5KsiNG9zpgmLCUYuLkxpLQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = URLUtils.get("marathon")+"/payMng/payNotify.shtml";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = URLUtils.get("marathon")+"/payMng/payReturn.shtml";
	
	// 签名方式
	public static String sign_type = "RSA";
	
	// 调试用，创建TXT日志文件夹路径，见AlipayCore.java类中的logResult(String sWord)打印方法。
	public static String log_path = "C:\\";
		
	// 字符编码格式 目前支持 gbk 或 utf-8
	public static String input_charset = "utf-8";
		
	// 支付类型 ，无需修改
	public static String payment_type = "1";
		
	// 调用的接口名，无需修改
	public static String service = "create_direct_pay_by_user";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
//↓↓↓↓↓↓↓↓↓↓ 请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓
	
	// 防钓鱼时间戳  若要使用请调用类文件submit中的query_timestamp函数
	public static String anti_phishing_key = "";
	
	// 客户端的IP地址 非局域网的外网IP地址，如：221.0.0.1
	public static String exter_invoke_ip = "";
		
//↑↑↑↑↑↑↑↑↑↑请在这里配置防钓鱼信息，如果没开通防钓鱼功能，为空即可 ↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑
	
}

