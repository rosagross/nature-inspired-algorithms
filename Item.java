
public class Item{

  private int weight;
  private int value;
  private boolean selected;

  public Item(int weight, int value){
    this.weight = weight;
    this.value = value;
  }

  public int getWeight(){
    return this.weight;
  }

  public int getValue(){
    return this.value;
  }

  public boolean getSelected(){
    return this.selected;
  }

  public void setSelected(boolean selected){
    this.selected = selected;
  }

}
