
public class Main {

    public static void main(String[] args) {
        var start = new Processor("./in/horse.jpg");
        // .rotatedNearestNeighbor(1)
        // .crop(10,10,300,300)
        // .translateNearestNeighbor(50,50)

        start.addLayer(i -> i.histogram(20000))//
                .save("./out/histogram.png")//
                .popLayer()
                // .brighten(20)
                // .addContrast(1.2f)
                .save("./out/done.png")//
                .push()//
                .histogram(20000)//
                .save("./out/histogram2.png")//
                .mergeLayers()
                .save("./out/merged.png")
                ;
    }
}
