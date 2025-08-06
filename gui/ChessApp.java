package gui;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import java.util.Optional;

import chess.Board;
import chess.Colour;
import chess.King;
import chess.Pawn;
import chess.Position;
import chess.Piece;
import chess.PieceType;
import chess.Queen;
import chess.Rook;
import chess.Bishop;
import chess.Knight;

public class ChessApp extends Application {
    Position position=null;
    Piece currPiece=null;
    List<Position> possibleMoves=new ArrayList<>();
    boolean whiteTurn=true;
    King kingWhite=(King)Piece.board.posMap.get(new Position(3,7));
    King kingBlack=(King)Piece.board.posMap.get(new Position(3,0));
    //To adjust Enpassent
    Pawn enpassentBlackPawn=null;
    Pawn enpassentWhitePawn=null;
    Label turnLabel=new Label("White's Turn");
    Map<Piece,List<Position>> checkMap=null;
    ImageView backGroundImage=new ImageView(getClass().getResource("/Images/homeBackground.png").toExternalForm());

    //constants
    private final int size=60;
    private final int gap=1;
    private final int margin=1;
    private final int width=size*8+gap*7+margin*2;
    private final int height=size*8+gap*7+margin*2;
    @Override
    public void start(Stage stage) {
        GridPane chessBoard=new GridPane();
        chessBoard.setPrefSize(width,height);
        chessBoard.setStyle("-fx-background-color:rgb(255, 255, 255,0.0);");
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                Position pos = new Position(i,j);
                StackPane stackPane=new StackPane();
                Square square=new Square(pos,size);
                square.setOnMouseClicked(e->{
                    handleSquareClick(stage,chessBoard,pos,square);
                });
                stackPane.getChildren().add(square);
                stackPane.getChildren().add(square.gridImageView);
                GridPane.setConstraints(stackPane,i,j);
                GridPane.setMargin(stackPane,new Insets(margin,margin,margin,margin));
                chessBoard.getChildren().add(stackPane);
            }
        }
        setup(chessBoard);
        Button resetButton=new Button("Reset");
        resetButton.setOnAction(e->{
           reset(chessBoard);
        });
        VBox gameBoard = new VBox();
        gameBoard.getChildren().add(turnLabel);
        gameBoard.getChildren().add(chessBoard);
        gameBoard.getChildren().add(resetButton);
        Scene gameScene=new Scene(gameBoard);

        StackPane backGround = new StackPane();
        backGroundImage.setFitWidth(width);
        backGroundImage.setFitHeight(height);
        backGround.getChildren().add(backGroundImage);
        VBox homeMenu = new VBox();
        homeMenu.setPrefSize(width,height);
        Button newGameButton=new Button("New Game");
        Button exitButton=new Button("Exit");
        Button rulesButton=new Button("Rules");
        homeMenu.setAlignment(Pos.CENTER);
        homeMenu.setPrefSize(width, height);
        homeMenu.setSpacing(gap);
        homeMenu.getChildren().add(newGameButton);
        homeMenu.getChildren().add(rulesButton);
        homeMenu.getChildren().add(exitButton);
        newGameButton.setOnAction(e->{
            reset(chessBoard);
            stage.setScene(gameScene);
        });

        exitButton.setOnAction(e->{
            stage.close();
        });
        rulesButton.setOnAction(e->{
            rulesButton.setText("Go search on Internet");
        });
        backGround.setPrefSize(width,height);
        backGround.getChildren().add(homeMenu);
        Scene homeScene=new Scene(backGround);

        stage.setTitle("Chess By Faisal");
        stage.getIcons().add(new Image(getClass().getResource("/Images/icon.png").toExternalForm()));
        stage.setResizable(false);
        stage.setScene(homeScene);
        stage.show();
    }
    public void pinningAdjustment(){
        Piece.board.pinPieces(kingWhite);
        Piece.board.pinPieces(kingBlack);
        Piece.board.releasePinnedPieces();
    }
    public void setCurrPiece(Piece piece){
        currPiece=piece;
        if(checkMap==null){
            possibleMoves=piece.canMove(null);
        } else {
            possibleMoves=checkMap.getOrDefault(piece,new ArrayList<>());
        }
    }
    public void enpassentAdjustment(){
        if(currPiece.clr==Colour.WHITE){
            if(enpassentWhitePawn!=null){
                enpassentWhitePawn.enpassent=false;
                enpassentWhitePawn=null;
            }
            if(currPiece.piece==PieceType.PAWN){
                enpassentWhitePawn=(Pawn)currPiece;
            }

        }else{
            if(enpassentBlackPawn!=null){
                enpassentBlackPawn.enpassent=false;
                enpassentBlackPawn=null;
            }
            if(currPiece.piece==PieceType.PAWN){
                enpassentBlackPawn=(Pawn)currPiece;
            }
        }        
    }
    public void pawnPromotion(Position pos,Stage stage,GridPane chessBoard){
        if(currPiece.piece==PieceType.PAWN && (currPiece.position.y==0 || currPiece.position.y==7)){
            Piece p = currPiece;
            Alert alert = new Alert(AlertType.CONFIRMATION);
            ButtonType queenButton = new ButtonType("Queen");
            ButtonType rookButton = new ButtonType("Rook");
            ButtonType bishopButton = new ButtonType("Bishop");
            ButtonType knightButton = new ButtonType("Knight");
            alert.setHeaderText("Promote " + p.clr + " " + p.piece + " to:");
            alert.getButtonTypes().setAll(queenButton,rookButton,bishopButton,knightButton);
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent()){
                if(result.get()==queenButton){
                    Piece.board.posMap.put(pos,new Queen(pos,p.clr));
                } else if(result.get()==rookButton){
                    Piece.board.posMap.put(pos,new Rook(pos,p.clr));
                } else if(result.get()==bishopButton){
                    Piece.board.posMap.put(pos,new Bishop(pos,p.clr));
                } else if(result.get()==knightButton){
                    Piece.board.posMap.put(pos,new Knight(pos,p.clr));
                }
            } else {
                Piece.board.posMap.put(pos,new Queen(pos,p.clr));
            }
            setup(chessBoard);
        }
    }
    public void handleSquareClick(Stage stage,GridPane chessBoard,Position pos,Square square){
        if (currPiece!=null && currPiece.clr==(whiteTurn?Colour.WHITE:Colour.BLACK)){
            if(square.active){
                //enpassent adjustment
                enpassentAdjustment();
                currPiece.move(pos);
                pawnPromotion(pos,stage,chessBoard);
                whiteTurn=!whiteTurn;
                currPiece=null;
                pinningAdjustment();
                checkMap=checkmateAdjustment(whiteTurn?kingWhite:kingBlack);
                if(gameover()){
                    turnLabel.setText("Game Over "+(whiteTurn?"Black Wins":"White Wins"));
                } else if(whiteTurn && kingWhite.isStalemate()||!whiteTurn && kingBlack.isStalemate()){
                    turnLabel.setText("Stalemate");
                }else {
                    turnLabel.setText(whiteTurn?"White's Turn":"Black's Turn");
                }
            }else{
                currPiece=Piece.board.posMap.get(pos);
                if(currPiece!=null && currPiece.clr!=(whiteTurn?Colour.WHITE:Colour.BLACK)){
                    currPiece=null;
                }
            }
            //deactivate all squares
            deactivateAllSquares(chessBoard);
            if(currPiece!=null){
                updateMoves(currPiece);
            }
        } else{
            Piece piece=Piece.board.posMap.get(pos);
            if(piece!=null && piece.clr==(whiteTurn?Colour.WHITE:Colour.BLACK)){
                currPiece=piece;
                updateMoves(currPiece);
            }
        }
        activateAllSquares(chessBoard);
        setup(chessBoard);
    }
    public void activateAllSquares(GridPane chessBoard){
        for(Position p:possibleMoves){
            ((Square)((StackPane)chessBoard.getChildren().get(p.x*8+p.y)).getChildren().get(0)).setActive(true);
        }
    }
    public void deactivateAllSquares(GridPane chessBoard){
        for(Position p:possibleMoves){
            ((Square)((StackPane)chessBoard.getChildren().get(p.x*8+p.y)).getChildren().get(0)).setActive(false);
        }
        possibleMoves.clear();
    }
    public Map<Piece,List<Position>> checkmateAdjustment(King king){
        return king.isCheckmate();
    }
    void updateMoves(Piece piece){
        if(checkMap==null){
            possibleMoves=piece.canMove(null);
        } else {
            possibleMoves=new ArrayList<>(checkMap.getOrDefault(piece,new ArrayList<>()));
        }
    }
    boolean gameover(){
        if(checkMap!=null && checkMap.isEmpty()){
            return true;
        }
        return false;
    }
    public void setup(GridPane board){
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                int t = i*8+j;
                ((StackPane)board.getChildren().get(t)).getChildren().removeIf(node->node instanceof ImageView && node!=((StackPane)board.getChildren().get(t)).getChildren().get(1));
            }
        }
        for(Map.Entry<Position,Piece> entry:Piece.board.posMap.entrySet()){
            Position pos=entry.getKey();
            Piece piece=entry.getValue();
            if(piece!=null){
                int size=60;
                ImageView imageView=piece.imageView;
                imageView.setFitWidth(size);
                ((StackPane)board.getChildren().get(pos.x*8+pos.y)).getChildren().add(imageView);
            }
        }
    }
    void reset(GridPane chessBoard){

        Piece.board=new Board();
        checkMap=null;
        whiteTurn=true;
        turnLabel.setText("White's Turn");
        setup(chessBoard);
        deactivateAllSquares(chessBoard);
        currPiece=null;
        possibleMoves.clear();
        kingWhite=(King)Piece.board.posMap.get(new Position(3,7));
        kingBlack=(King)Piece.board.posMap.get(new Position(3,0));
    }
    public static void main(String[] args) {
        launch(args);
    }
}
