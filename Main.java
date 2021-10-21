
public class Main {

    public static void main(String[] args) {
        var start = new Processor("./in/horse.jpg").grayscale();
        // .rotatedNearestNeighbor(1)
        // .crop(10,10,300,300)
        // .translateNearestNeighbor(50,50)
        System.out.println(start.getPixel(0,0));
        
        for(var i = 1; i < 10; i++){
            start.push()
            .brighten(i * 10)
            .saveCurrentLayer("./out/brighten" + i * 10 + ".png")
            .popLayer()
            ;
            System.out.println(start.getPixel(0,0));
            
        }
        System.out.println(start.getPixel(0,0));
        start.saveCurrentLayer("./out/Now.png");

        start.addLayer(i -> i.histogram())//
                .saveCurrentLayer("./out/histogram.png")//
                .popLayer()
                // .brighten(20)
                // .addContrast(1.2f)
                .saveCurrentLayer("./out/done.png")//
                .push()//
                .histogram(100)//
                .saveCurrentLayer("./out/histogram2.png")//
                .mergeLayers()
                .saveCurrentLayer("./out/merged.png")
                ;

        
        
    }
}
