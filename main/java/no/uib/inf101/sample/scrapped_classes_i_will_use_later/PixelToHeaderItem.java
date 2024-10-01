package no.uib.inf101.sample.scrapped_classes_i_will_use_later;

import no.uib.inf101.sample.view.GameView;

/*
 * Class converting pixels to header items
 * ------ SCRAPPED ------
 */
public class PixelToHeaderItem {
    private GameView view;

    public PixelToHeaderItem(GameView view){
        this.view = view;
    }

    public void selectHeaderItem(){

    }


    /**
     * Checks if the mouse's position is within the header box
     * @param mouseX - the given x value
     * @param mouseY - the given y value
     * @return true if the mouse is within the header
     */
    public boolean isMouseWithinHeader(double mouseX, double mouseY){
        //Removing the padding from the header box itself
        mouseX -= this.view.getMargin();
        mouseY -= this.view.getMargin();
      
        // Checks if mouse is lower than header box
        if(mouseX < 0 || mouseY < 0){
            return false;
        }
        // Checks if mouse is larger than headerbox
        if((this.view.getWidth() < mouseX) || (this.view.getHeaderHeight() < mouseY)){
            System.out.println("what");
            return false;
        }
        System.out.println("bussin");
        return true;
    }
}
