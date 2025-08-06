package gui;
import chess.Position;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
public class Square extends Rectangle{
    ImageView gridImageView;
    public Position position;
    boolean active=false;
    public Square(Position position,int size){
        super(size,size);
        this.position=position;
        if((position.x%2==0 && position.y%2==0) || (position.x%2!=0 && position.y%2!=0)){
            this.setFill(Color.rgb(237, 227, 116));
        }else{
            this.setFill(Color.rgb(61, 219, 25));
        }
        // this.setStroke(Color.BLACK);
        Image gridImage=new Image(getClass().getResource("/Images/grid.png").toExternalForm());
        gridImageView=new ImageView(gridImage);
        gridImageView.setFitWidth(size);
        gridImageView.setFitHeight(size);
        gridImageView.setMouseTransparent(true);
        gridImageView.setVisible(false);
    }
    public void setActive(boolean active){
        this.active=active;
        if(active){
            this.setStyle("-fx-border-color: red; -fx-border-width: 3;");
            gridImageView.setVisible(true);
        }else{
            this.setStyle("-fx-border-color: transparent; -fx-border-width: 0;");
            gridImageView.setVisible(false);
        }
    }
}

