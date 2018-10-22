package theotherhattrick;

public class Prop {
    private int type; //Carrot = 1, Lettuce = 2, Hat = 3, Rabbit = 4, Other rabbit = 5

    private String name;

    private boolean state; //false pour carte cachée et true pour carte visible
    
    public Prop(String name, int type) {
    	this.name = name;
    	this.type = type;
    }

    public void hide() {
    	this.state = false;
    }

    public void unhide() {
    	this.state = true;
    }
    
    public boolean getState() {
    	return this.state;
    }

}
