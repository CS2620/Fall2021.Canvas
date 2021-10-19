import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class Processor {

  List<ImageLayer> layers = new ArrayList<>();
  int currentLayer = -1;
  int canvasWidth;
  int canvasHeight;

  public Processor(String filename) {
    try {
      BufferedImage bufferedImage = ImageIO.read(new File(filename));
      //layers.add(new ImageLayer(new Image(bufferedImage)));
      addLayer(new ImageLayer(new IPImage(bufferedImage)));
      inferCanvasSize(bufferedImage);

    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }

  public Processor(BufferedImage bi) {
    addLayer(new ImageLayer(new IPImage(bi)));
    //layers.add(new ImageLayer(new Image(bi)));
    inferCanvasSize(bi);
  }

  private void inferCanvasSize(BufferedImage bi) {
    this.canvasWidth = bi.getWidth();
    this.canvasHeight = bi.getHeight();
  }

  public ImageLayer currentLayer() {
    return this.layers.get(currentLayer);
  }

  public Processor push() {
    layers.add(currentLayer().clone());
    resetCurrentLayer();
    return this;
  }

  private void resetCurrentLayer() {
    currentLayer = layers.size() - 1;
  }

  public Processor popLayer() {
    // this.imageLayer = stack.pop();
    layers.remove(currentLayer);
    currentLayer--;
    if (currentLayer < 0)
      currentLayer = 0;
    return this;
  }

  public IPImage image() {
    return this.currentLayer().image();
  }

  public Processor histogram(int i) {
    currentLayer().image().histogram(i);
    return this;
  }

  public Processor save(String string) {
    this.saveLayer(string);
    return this;
  }

  public Processor brighten(int i) {
    currentLayer().image().brighten(i);
    return this;
  }

  public Processor addContrast(float f) {
    currentLayer().image().addContrast(f);
    return this;
  }

  public Processor addLayer(IImageFunction i) {
    this.layers.add(new ImageLayer(i.run(currentLayer().image().clone())));
    resetCurrentLayer();

    return this;

  }

  public Processor addLayer(ImageLayer layer){
    layers.add(layer);
    resetCurrentLayer();
    return this;
  }

  public Processor addLayer(BufferedImage bufferedImage){
    layers.add(new ImageLayer(new IPImage(bufferedImage)));
    resetCurrentLayer();
    return this;
  }

  public Processor saveLayer(String string) {
    currentLayer().image().save(string);
    return this;
  }

  public Processor mergeLayers() {
    // Replace layers with a new rasterized copy of everything
    BufferedImage merged = new BufferedImage(canvasWidth, canvasHeight, BufferedImage.TYPE_4BYTE_ABGR);

    Graphics2D g = (Graphics2D)merged.getGraphics();

    for(ImageLayer layer : layers){
      g.drawImage(layer.image().image, 0, 0, null);
    }

    g.dispose();


    this.clearLayers();
    this.addLayer(merged);
    return this;
  }

  private void clearLayers() {
    layers.clear();
    this.currentLayer = -1;
  }

  

}
