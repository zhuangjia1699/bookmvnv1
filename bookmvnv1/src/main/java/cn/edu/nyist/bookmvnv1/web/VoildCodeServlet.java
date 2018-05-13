package cn.edu.nyist.bookmvnv1.web;

import java.awt.Color;  
import java.awt.Font;  
import java.awt.Graphics2D;  
import java.awt.image.BufferedImage;  
import java.io.IOException;  
import java.util.Random;  

import javax.imageio.ImageIO;  
import javax.servlet.ServletConfig;  
import javax.servlet.ServletException;  
import javax.servlet.ServletOutputStream;  
import javax.servlet.annotation.WebInitParam;  
import javax.servlet.annotation.WebServlet;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  

@WebServlet(value="/vcode.png",initParams=
{@WebInitParam(name="width",value="80"),@WebInitParam(name="height",value="30")})
public class VoildCodeServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;  
	/**  
	* 验证码图⽚的宽度。  
	*/  
	private int width = 60;  
	
	/**  
	* 验证码图⽚的⾼度。  36
	*/  
	private int height = 20;  
	
	/**  
	* 验证码字符个数  
	*/  
	private int codeCount = 4;  
	
	/**  
	* xx  
	*/  
	private int xx = 0;  
	
	/**  
	* 字体⾼度  
	*/  
	private int fontHeight;  
	
	/**  
	* codeY  
	*/  
	private int codeY;  
	
	/**  
	8 ⽤户管理⼩系统
	61/110
	* codeSequence  
	*/  
	char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K',
	'L', 'M', 'N', 'O', 'P', 'Q', 'R',
	
	'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5',
	'6', '7', '8', '9' };
	
	
	@Override  
	public void init(ServletConfig config) throws ServletException {  
	// 从web.xml中获取初始信息  
	// 宽度  
	String strWidth = config.getInitParameter("width");//读取初始化参数  
	// ⾼度  
	String strHeight = config.getInitParameter("height");  
	// 字符个数  
	String strCodeCount = config.getInitParameter("codeCount");  
	
	// 将配置的信息转换成数值  
	try {  
	//如果你配置了图⽚的宽度，⽤你配置；如果你⽊有配置，就默认值  78
	if (strWidth != null && strWidth.length() != 0) {  
	width = Integer.parseInt(strWidth);  
	}  
	if (strHeight != null && strHeight.length() != 0) {  
	height = Integer.parseInt(strHeight);  
	}  
	if (strCodeCount != null && strCodeCount.length() != 0) {  
	codeCount = Integer.parseInt(strCodeCount);  
	}  
	} catch (NumberFormatException e) {  
	e.printStackTrace();  
	}  
	
	xx = width / (codeCount + 1);  
	fontHeight = height - 2;  
	codeY = height - 4;  
	
	}  
	
	/** 
	* @see HttpServlet#HttpServlet()  
	*/  
	public VoildCodeServlet() {  
	super();  
	}  
	
	protected void doGet(HttpServletRequest request, HttpServletResponse
	response)
	
	throws ServletException, IOException {  
	// 定义图像buffer 
	BufferedImage buffImg = new BufferedImage(width, height,
	BufferedImage.TYPE_INT_RGB);
	
	Graphics2D gd = buffImg.createGraphics(); 
	
	// 创建⼀个随机数⽣成器类  
	Random random = new Random(); 
	
	// 将图像填充为⽩⾊:清屏  114
	gd.setColor(Color.WHITE); 
	gd.fillRect(0, 0, width, height); 
	
	// 创建字体，字体的⼤⼩应该根据图⽚的⾼度来定。  118
	Font font = new Font("Fixedsys", Font.PLAIN, fontHeight); 
	// 设置字体。  120
	gd.setFont(font); 
	
	// 画边框。  
	gd.setColor(Color.BLACK); 
	gd.drawRect(0, 0, width - 1, height - 1); 
	
	// 随机产⽣160条⼲扰线，使图象中的认证码不易被其它程序探测到。  1
	gd.setColor(Color.BLACK); 
	for (int i = 0; i < 30; i++) { 
	int x = random.nextInt(width); 
	int y = random.nextInt(height); 
	int xl = random.nextInt(12); 
	int yl = random.nextInt(12); 
	gd.drawLine(x, y, x + xl, y + yl); 
	} 
	
	// randomCode⽤于保存随机产⽣的验证码，以便⽤户登录后进⾏验证。  
	StringBuffer randomCode = new StringBuffer(); 
	int red = 0, green = 0, blue = 0; 
	
	// 随机产⽣codeCount数字的验证码。  
	for (int i = 0; i < codeCount; i++) { 
	// 得到随机产⽣的验证码数字。  
	String strRand = String.valueOf(codeSequence[random.nextInt(36)]);
	
	// 产⽣随机的颜⾊分量来构造颜⾊值，这样输出的每位数字的颜⾊值都将不同。  
	red = random.nextInt(255); 
	green = random.nextInt(255); 
	blue = random.nextInt(255); 
	
	// ⽤随机产⽣的颜⾊将验证码绘制到图像中。  150
	gd.setColor(new Color(red, green, blue)); 
	gd.drawString(strRand, (i + 1) * xx, codeY); 
	
	// 将产⽣的四个随机数组合在⼀起。  154
	randomCode.append(strRand); 
	} 
	// 将四位数字的验证码保存到Session中。  
	HttpSession session = request.getSession(); 
	session.setAttribute("validateCode", randomCode.toString()); 
	
	// 禁⽌图像缓存。  
	response.setHeader("Pragma", "no-cache"); 
	response.setHeader("Cache-Control", "no-cache"); 
	response.setDateHeader("Expires", 0); 
	
	response.setContentType("image/jpeg"); 
	
	// 将图像输出到Servlet输出流中。  
	ServletOutputStream sos = response.getOutputStream(); 
	ImageIO.write(buffImg, "jpeg", sos); 
	sos.close(); 
	
	
	}  
	
	/**  1
	* @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse  177
	* response)  
	*/  
	protected void doPost(HttpServletRequest request, HttpServletResponse
	response)
	
	throws ServletException, IOException {  
	// TODO Auto-generated method stub  
	doGet(request, response);  
	}  

}

