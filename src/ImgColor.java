import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;


public class ImgColor {
	int high,width,high1,width1;
	int[] pixels;
	BufferedImage image;
	BufferedImage image2;

	public void Load(String path) {
		try {
			this.image = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.high = this.image.getHeight();
		this.width = this.image.getWidth();
	}

	public void Load2(String path) {
		try {
			this.image2 = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.high1 = this.image2.getHeight();
		this.width1 = this.image2.getWidth();
	}

	public void Display() {
		JFrame frame = new JFrame();

		JLabel lblimage = new JLabel(new ImageIcon(this.image));
		frame.getContentPane().add(lblimage, BorderLayout.CENTER);
		frame.setSize(this.width, this.high);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void Display2() {
		JFrame frame = new JFrame();

		JLabel lblimage = new JLabel(new ImageIcon(this.image2));
		frame.getContentPane().add(lblimage, BorderLayout.CENTER);
		frame.setSize(this.width1, this.high1);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}


	private int calcmedian(int[] v){	    
		Arrays.sort(v);
		return(v[v.length/2]);
	}


	public void OnlyRed(boolean MonocromaticFlag) {
		this.pixels = this.image.getRGB(0, 0, this.width, this.high, null, 0, this.width);
		for (int i = 0; i < this.pixels.length; i++) {
			Color c = new Color(this.pixels[i]);
			int red = c.getRed();
			Color tmp ;
			if(MonocromaticFlag) {
				tmp = new Color(red, red, red);
			} else {
				tmp = new Color(red, 0, 0);
			}
			int aux = tmp.getRGB();
			this.pixels[i] = aux;
		}
		this.image.setRGB(0, 0, this.width, this.high, this.pixels, 0, this.width);
	}


	public void OnlyBlue(boolean MonocromaticFlag) {
		this.pixels = this.image.getRGB(0, 0, this.width, this.high, null, 0, this.width);
		for (int i = 0; i < this.pixels.length; i++) {
			Color c = new Color(this.pixels[i]);
			int blue = c.getBlue();

			Color tmp ;
			if(MonocromaticFlag) {
				tmp = new Color(blue, blue, blue);
			} else {
				tmp = new Color(0, 0, blue);
			}
			int aux = tmp.getRGB();
			this.pixels[i] = aux;
		}
		this.image.setRGB(0, 0, this.width, this.high, this.pixels, 0, this.width);
	}


	public void OnlyGreen( boolean MonocromaticFlag) {
		this.pixels = this.image.getRGB(0, 0, this.width, this.high, null, 0, this.width);
		for (int i = 0; i < this.pixels.length; i++) {
			Color c = new Color(this.pixels[i]);
			int green = c.getGreen();
			Color tmp ;

			if(MonocromaticFlag) {
				tmp = new Color(green, green, green);
			} else {
				tmp = new Color(0, green, 0);
			}
			int aux = tmp.getRGB();
			this.pixels[i] = aux;
		}
		this.image.setRGB(0, 0, this.width, this.high, this.pixels, 0, this.width);
	}


	public void NegativeRGB() {
		this.pixels = this.image.getRGB(0, 0, this.width, this.high, null, 0, this.width);
		for (int i = 0; i < this.pixels.length; i++) {
			Color c = new Color(this.pixels[i]);
			int red = 255 - c.getRed();
			int green = 255 -  c.getGreen();
			int blue = 255 - c.getBlue();

			Color tmp = new Color(red, green, blue);
			int aux = tmp.getRGB();
			this.pixels[i] = aux;
		}
		this.image.setRGB(0, 0, this.width, this.high, this.pixels, 0, this.width);
	}

	public void negativeYUV(YUVPixel[][] matrix){
		BufferedImage imgOut = new BufferedImage(this.width, this.high, this.image.getType());
		
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				matrix[i][j].setY(255 - matrix[i][j].getY());
				
				int _y = round(matrix[i][j].y);
				int _u = round(matrix[i][j].u);
				int _v = round(matrix[i][j].v);
				
				_y = UpperBound(_y);
				_u = UpperBound(_u);
				_v = UpperBound(_v);
				_y = LowerBound(_y);
				_u = LowerBound(_u);
				_v = LowerBound(_v);

				imgOut.setRGB(i,j,new Color(_y,_u,_v).getRGB());
				
			}
		}
		this.image = imgOut;
	}

	public void addBrightnessRGB(int offset) {

		this.pixels = this.image.getRGB(0, 0, this.width, this.high, null, 0, this.width);

		for (int i = 0; i < this.pixels.length; i++) {
			Color c = new Color(this.pixels[i]);
			int red = c.getRed() + offset;
			int green = c.getGreen() + offset;
			int blue = c.getBlue() + offset;

			red = UpperBound(red);
			blue = UpperBound(blue);
			green = UpperBound(green);
			red = LowerBound(red);
			blue = LowerBound(blue);
			green = LowerBound(green);

			Color tmp = new Color(red, green, blue);
			int aux = tmp.getRGB();
			this.pixels[i] = aux;
		}
		this.image.setRGB(0, 0, this.width, this.high, this.pixels, 0, this.width);
	}
	
	public void addBrightnessYUV(YUVPixel[][] matrix, double offset){
		BufferedImage imgOut = new BufferedImage(this.width, this.high, this.image.getType());

		double y;
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				
				y = matrix[i][j].getY() + offset;
				
				
				matrix[i][j].setY(y);
				int _y = round(matrix[i][j].y);
				int _u = round(matrix[i][j].u);
				int _v = round(matrix[i][j].v);
				
				_y = UpperBound(_y);
				_u = UpperBound(_u);
				_v = UpperBound(_v);
				_y = LowerBound(_y);
				_u = LowerBound(_u);
				_v = LowerBound(_v);
				imgOut.setRGB(i,j,new Color(_y,_u,_v).getRGB());

			}
		}
		this.image = imgOut;
	}


	private int UpperBound(int color) {
		if(color > 255) {
			return 255;
		}
		return color;
	}

	private int LowerBound(int color) {
		if(color < 0) {
			return 0;
		}
		return color;
	}


	public void BrightnessMultRGB(int offset) {

		this.pixels = this.image.getRGB(0, 0, this.width, this.high, null, 0, this.width);

		for (int i = 0; i < this.pixels.length; i++) {
			Color c = new Color(this.pixels[i]);
			int red = c.getRed() * offset;
			int green = c.getGreen() * offset;
			int blue = c.getBlue() * offset;

			red = UpperBound(red);
			blue = UpperBound(blue);
			green = UpperBound(green);
			red = LowerBound(red);
			blue = LowerBound(blue);
			green = LowerBound(green);

			Color tmp = new Color(red, green, blue);
			int aux = tmp.getRGB();
			this.pixels[i] = aux;
		}
		this.image.setRGB(0, 0, this.width, this.high, this.pixels, 0, this.width);
	}
	
	
	public void BrightnessMultYUV(YUVPixel[][] matrix, double offset){
		BufferedImage imgOut = new BufferedImage(this.width, this.high, this.image.getType());

		double y;
		for(int i = 0; i < matrix.length; i++){
			for(int j = 0; j < matrix[i].length; j++){
				
				y = matrix[i][j].getY() * offset;
				
				
				matrix[i][j].setY(y);
				int _y = round(matrix[i][j].y);
				int _u = round(matrix[i][j].u);
				int _v = round(matrix[i][j].v);
				
				_y = UpperBound(_y);
				_u = UpperBound(_u);
				_v = UpperBound(_v);
				_y = LowerBound(_y);
				_u = LowerBound(_u);
				_v = LowerBound(_v);
				imgOut.setRGB(i,j,new Color(_y,_u,_v).getRGB());

			}
		}
		this.image = imgOut;
	}


	public void RGB2YUV(YUVPixel[][] matrix){
		BufferedImage imgOut = new BufferedImage(this.width, this.high, this.image.getType());
		int r, g ,b;
		for(int i = 0; i < this.image.getWidth(); i++){
			for(int j = 0; j < this.image.getHeight(); j++){
				Color color = new Color(this.image.getRGB(i,j));
				r = color.getRed();
				g = color.getGreen();
				b = color.getBlue();

				if(matrix[i][j] == null){
					matrix[i][j] = new YUVPixel(0.299*r + 0.587*g + 0.114*b,
												-0.147*r - 0.289*g + 0.436*b,
												0.615*r - 0.515*g - 0.100*b);
				}else{
					matrix[i][j].setY(0.299*r + 0.587*g + 0.114*b);
					matrix[i][j].setU(-0.147*r - 0.289*g + 0.436*b);
					matrix[i][j].setV(0.615*r - 0.515*g - 0.100*b);
				}
				
				int _y = round(matrix[i][j].y);
				int _u = round(matrix[i][j].u);
				int _v = round(matrix[i][j].v);
				
				_y = UpperBound(_y);
				_u = UpperBound(_u);
				_v = UpperBound(_v);
				_y = LowerBound(_y);
				_u = LowerBound(_u);
				_v = LowerBound(_v);

				imgOut.setRGB(i,j,new Color(_y,_u,_v).getRGB());
			}

		}
		this.image = imgOut;
	}

	public void YUV2RGB(YUVPixel[][] matrix){
		BufferedImage imgOut = new BufferedImage(this.width, this.high, this.image.getType());
		int r,g,b;
		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.high; j++){
				r = round(matrix[i][j].getY() + 1.398*matrix[i][j].getV());
				g = round(matrix[i][j].getY() - 0.395*matrix[i][j].getU() - 0.581*matrix[i][j].getV());
				b = round(matrix[i][j].getY() + 2.032*matrix[i][j].getU());

				r = UpperBound(r);
				b = UpperBound(b);
				g = UpperBound(g);
				r = LowerBound(r);
				b = LowerBound(b);
				g = LowerBound(g);

				imgOut.setRGB(i,j,new Color(r,g,b).getRGB());
			}
		}
		this.image = imgOut;
	}


	private int round(double d){

		double dAbs = Math.abs(d);
		int i = (int) dAbs;
		double result = dAbs - (double) i;
		if(result<0.5){
			return d<0 ? -i : i;            
		}else{
			return d<0 ? -(i+1) : i+1;          
		}
	}

	public void average(int n) {
		if(n % 2 == 0) {
			System.out.println("Mask size must be an even number");
			return;
		}
		BufferedImage imgOut = new BufferedImage(this.width, this.high, this.image.getType());

		for(int i = 0; i <  this.high; i++){
			for(int j = 0; j < this.width; j++){
				int mediaR = 0;
				int mediaG = 0;
				int mediaB = 0;
				for(int h = i-n/2; h <= i+n/2; h++){
					for(int w = j-n/2; w <= j+n/2; w++){
						if(h >= 0 && h < this.high && w >= 0 && w < this.width){
							Color imgColor2 = new Color(image.getRGB(h, w));
							mediaR += imgColor2.getRed();
							mediaG += imgColor2.getGreen();
							mediaB += imgColor2.getBlue();
						}
					}					
				}				
				imgOut.setRGB(i, j, new Color(mediaR / (n*n), mediaG / (n*n), mediaB / (n*n)).getRGB());                  
			}
		}
		this.image = imgOut;
	}


	public void Median(int n) {
		BufferedImage imgOut = new BufferedImage(this.width, this.high, this.image.getType());

		for(int i = 1; i < this.high-1; i++){
			for(int j = 1; j < this.width-1; j++){
				int aux = -1;
				int medianR[] = new int[n*n];
				int medianG[] = new int[n*n];
				int medianB[] = new int[n*n];

				for(int h = i-n/2; h <= i+n/2; h++){
					for(int w = j-n/2; w <= j+n/2; w++){
						aux++;
						if(h >= 0 && h < this.high && w >= 0 && w < this.high){
							Color imgColor = new Color(this.image.getRGB(h, w));
							medianR[aux] = imgColor.getRed(); 
							medianG[aux] = imgColor.getGreen();
							medianB[aux] = imgColor.getBlue();
						}else{
							medianR[aux] = 0; 
							medianG[aux] = 0;
							medianB[aux] = 0;
						}
					}					
				}		
				int R = calcmedian(medianR);
				int G = calcmedian(medianG);
				int B = calcmedian(medianB);

				imgOut.setRGB(i, j, new Color(R, G, B).getRGB());
			}
		}
		this.image = imgOut;
	}


	public void TwoImages() {
		if(this.image2 == null || this.high != this.high1 || this.width != this.width1 ) {
			System.out.println("You have to load two images with the same size");
			return;
		}
		BufferedImage imgOut = new BufferedImage(this.width, this.high, this.image.getType());

		for(int i = 0; i < this.width; i++){
			for(int j = 0; j < this.high; j++){
				Color imgColor1 = new Color(this.image.getRGB(i, j));
				Color imgColor2 = new Color(this.image2.getRGB(i, j));

				int r = (imgColor1.getRed() + imgColor2.getRed()) / 2;
				int g = (imgColor1.getGreen() + imgColor2.getGreen()) / 2;
				int b = (imgColor1.getBlue() + imgColor2.getBlue()) / 2;

				imgOut.setRGB(i, j, new Color(r, g, b).getRGB());
			}
		}
		this.image = imgOut;
	}


//	public void AdpBrightness(int c, int n) {
//		BufferedImage imgOut = new BufferedImage(this.width, this.high, this.image.getType());
//		for (int i = 0; i < this.high; i++) {
//			for (int j = 0; j < this.width; j++) {
//				Color imgColor = new Color(this.image.getRGB(i, j));
//				int r  = imgColor.getRed();
//				int g = imgColor.getGreen();
//				int b = imgColor.getBlue();
//
//
//			}
//		}
//
//
//	}
//
//	public int Var(int n) {
//		int aux = 0;
//		for (int i = 0; i < n; i++) {
//			for (int j = 0; j < n; j++) {
//				Color color = new Color(this.image.getRGB(i, j));
//
//
//			}
//		}
//		return n;
//	}
//
//
//

}









