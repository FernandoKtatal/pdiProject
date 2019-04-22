

public class Main {

	public static void main(String[] args) {
		ImgColor img = new ImgColor();
		String path = "/Users/fernandosouza/Desktop/PDI/deserto.jpg";
		String path2 ="/Users/fernandosouza/Desktop/PDI/paisagem.jpg";
		
		img.Load(path);
		img.Load2(path2);
		
		YUVPixel[][] matrix = new YUVPixel[img.image.getWidth()][img.image.getHeight()];
				
		
		
//		img.OnlyRed(false);
//		img.OnlyBlue(true);
//		img.OnlyGreen(true);
//		img.NegativeRGB();
//		img.addBrightnessRGB(100);
//		img.BrightnessMultRGB(5);
//		img.RGB2YUV(matrix);
//		img.addBrightnessYUV(matrix, 100);
//		img.BrightnessMultYUV(matrix, 2);
//		img.negativeYUV(matrix);
//		img.YUV2RGB(matrix);
//		img.average(7);
//		img.Median(7);
		img.TwoImages();
//		img.CheckImg();
		img.Display();
//		img.Display2();



		
	}
}
	



