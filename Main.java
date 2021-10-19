
public class Main {

    public static void main(String[] args) {
        var start = new Processor("horse.jpg");
        // .rotatedNearestNeighbor(1)
        // .crop(10,10,300,300)
        // .translateNearestNeighbor(50,50)

        start.addLayer(i -> i.histogram(20000))//
                .save("histogram.png")//
                .popLayer()
                // .brighten(20)
                // .addContrast(1.2f)
                .save("done.png")//
                .push()//
                .histogram(20000)//
                .save("histogram2.png")//
                .mergeLayers()
                .save("merged.png")
                ;
    }
}
