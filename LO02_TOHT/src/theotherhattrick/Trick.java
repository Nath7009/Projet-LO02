package theotherhattrick;

public class Trick {
    private Prop[][] ingredients;

    private int points;

    private String name;
    
    public Trick(String name,Prop[][] ingredients, int points){
    	this.name = name;
    	this.ingredients = ingredients;
    	this.points = points;
    }

}
