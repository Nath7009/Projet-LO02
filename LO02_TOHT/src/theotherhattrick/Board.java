package theotherhattrick;

import java.util.ArrayList;
import java.util.Stack;
import java.util.Collections;


public class Board {
	private Stack<Trick> tricks;
	private Stack depiledTricks;

    private Prop[] props;
    
    public Board() {
    	this.createCards();
    }

    public void distributeCards() {
    	
    
    }
    
    public void createCards() {
    	
    	//Création des props
    	props = new Prop[7];
    	
    	Prop carrot = new Prop("Carrot", 1);
    	Prop lettuce = new Prop("Lettuce", 2);
    	Prop hat = new Prop("Hat", 3);
    	Prop rabbit = new Prop("The Rabbit", 4);
    	Prop oRabbit = new Prop("The Other Rabbit", 5);
    	
    	for(int i=0;i<3;i++) {
    		props[i] = carrot;
    	}
    	
    	props[3] = lettuce;
    	props[4] = hat;
    	props[5] = rabbit;
    	props[6] = oRabbit;
    	
    	//Création des tricks
    	ArrayList<Trick> tTricks = new ArrayList<Trick>(10);
    	//Liste permettant de stocker les tricks, on utilise une liste parcequ'elle peut être mélangée
    	//Ensuite, on place l'arraylist dans une pile
    	
    	tTricks.add(new Trick("The Hungry Rabbit",new Prop[][]{{rabbit, oRabbit}, {carrot, lettuce}} ,1));
    	tTricks.add(new Trick("The Bunch of Carrots",new Prop[][]{{carrot}, {carrot}} ,2));
    	tTricks.add(new Trick("The Vegetable Patch",new Prop[][]{{carrot}, {lettuce}} ,3));
    	tTricks.add(new Trick("The Rabbit That Didn't Like Carrots",new Prop[][]{{rabbit, oRabbit}, {lettuce}} ,4));
    	tTricks.add(new Trick("The Pair of Rabbits",new Prop[][]{{rabbit}, {oRabbit}} ,5));
    	tTricks.add(new Trick("The Vegetable Hat Trick",new Prop[][]{{hat}, {carrot, lettuce}} ,2));
    	tTricks.add(new Trick("The Carrot Hat Trick",new Prop[][]{{hat}, {carrot}} ,3));
    	tTricks.add(new Trick("The Slightly Easier Hat Trick",new Prop[][]{{hat}, {rabbit, oRabbit}} ,4));
    	tTricks.add(new Trick("The Hat Trick",new Prop[][]{{hat}, {rabbit}} ,5));
    	
    	Collections.shuffle(tTricks);    	
    	tTricks.add(new Trick("The Other Hat Trick",new Prop[][]{{hat}, {oRabbit}} ,6));
    	
    	tricks = new Stack<Trick>();
		tTricks.get(0).print();

    	
    	for(int i=tTricks.size()-1;i>=0;i--) {
    		tricks.push(tTricks.get(i));
    	}
    }
    
    public void printTricks() {
    	
    }

    public void comparePropsToTrick() {
    }

    public void createCopy(Prop prop) {
    }

    public void createCopy(Trick trick) {
    }

    public void replace() {
    }

    public void depile() {
    }

    public void revealProp() {
    }

    Prop[] getProps() {
        // Automatically generated method. Please delete this comment before entering specific code.
        return this.props;
    }

    void setProps(Prop[] value) {
        // Automatically generated method. Please delete this comment before entering specific code.
        this.props = value;
    }

}
