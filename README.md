# Chess Game by Faisal

A complete chess game implementation in Java with JavaFX GUI, featuring all standard chess rules including special moves like castling, en passant, and pawn promotion.

## 🎮 Features

### Core Chess Rules
- **Complete Chess Logic**: All standard chess piece movements and rules
- **Special Moves**:
  - **Castling**: King-side and Queen-side castling with proper validation
  - **En Passant**: Pawn capture en passant
  - **Pawn Promotion**: Automatic promotion dialog (Queen, Rook, Bishop, Knight)
- **Game State Management**:
  - Check detection and validation
  - Checkmate detection with legal move filtering
  - Stalemate detection
  - Turn-based gameplay (White/Black)

### Advanced Features
- **Piece Pinning**: Pieces can be pinned to protect the king
- **Move Validation**: All moves are validated to ensure they don't put the king in check
- **Visual Feedback**: Highlighted possible moves for selected pieces
- **Game Reset**: Ability to reset the game at any time

### User Interface
- **Modern GUI**: Clean, intuitive JavaFX interface
- **Home Menu**: Welcome screen with New Game, Rules, and Exit options
- **Game Board**: 8x8 chess board with piece images
- **Turn Indicator**: Shows whose turn it is (White/Black)
- **Game Status**: Displays checkmate, stalemate, or winner information

## 🏗️ Project Structure

```
Chess/
├── chess/                    # Core chess logic
│   ├── Board.java           # Board management and game state
│   ├── King.java            # King piece logic (castling, checkmate)
│   ├── Queen.java           # Queen piece logic
│   ├── Rook.java            # Rook piece logic
│   ├── Bishop.java          # Bishop piece logic
│   ├── Knight.java          # Knight piece logic
│   ├── Pawn.java            # Pawn piece logic (en passant, promotion)
│   ├── Piece.java           # Base piece class
│   ├── Position.java        # Position/coordinates class
│   ├── Colour.java          # Color enum (WHITE/BLACK)
│   ├── PieceType.java       # Piece type enum
│   ├── DIRECTION.java       # Direction enum for pinning
│   └── Interact.java        # Game interaction logic
├── gui/                     # User interface
│   ├── ChessApp.java        # Main application class
│   ├── Square.java          # Individual square component
│   └── PawnPromote.java     # Pawn promotion dialog
├── Images/                  # Game assets
│   ├── Chess_*.png          # Piece images (white/black)
│   ├── grid.png             # Board grid image
│   ├── homeBackground.png   # Home menu background
│   └── icon.png             # Application icon
├── javafx/                  # JavaFX runtime (included)
├── ChessApp.jar            # Executable JAR file
├── run.bat                 # Windows run script
├── run_modified.bat        # Development run script
└── README.md               # This file
```

## 🚀 Installation & Setup

### Prerequisites
- **Java 11 or higher** (JDK recommended)
- **Windows OS** (for .bat scripts)

### Quick Start
1. **Download/Clone** the project to your local machine
2. **Run the game** using one of the provided scripts:
   - `run.bat` - Run the pre-compiled version
   - `run_modified.bat` - Compile and run from source

### Manual Compilation (Optional)
If you want to compile from source:

```bash
# Compile the project
javac --module-path "javafx/lib" --add-modules javafx.controls -d out chess/*.java gui/*.java

# Create JAR file
jar --create --file ChessAppMod.jar --main-class=gui.ChessApp -C out .

# Run the compiled version
java --module-path javafx/lib --add-modules javafx.controls,javafx.fxml -jar ChessAppMod.jar
```

## 🎯 How to Play

### Starting the Game
1. Run `run.bat` or `run_modified.bat`
2. Click "New Game" from the home menu
3. The game starts with White's turn

### Making Moves
1. **Click on a piece** to select it
2. **Valid moves are highlighted** on the board
3. **Click on a highlighted square** to move the piece
4. **Turns alternate** between White and Black

### Special Moves
- **Castling**: Move the king two squares toward a rook (if conditions are met)
- **En Passant**: Capture an opponent's pawn that just moved two squares
- **Pawn Promotion**: When a pawn reaches the opposite end, choose promotion piece

### Game End Conditions
- **Checkmate**: King is in check with no legal moves
- **Stalemate**: No legal moves but king is not in check
- **Reset**: Click "Reset" button to start a new game

## 🔧 Technical Details

### Architecture
- **MVC Pattern**: Separation of game logic (Model) and UI (View)
- **Object-Oriented Design**: Each piece type extends the base Piece class
- **Event-Driven**: JavaFX event handling for user interactions

### Key Classes
- **`ChessApp`**: Main application controller
- **`Board`**: Manages game state and piece positions
- **`King`**: Handles checkmate detection and castling
- **`Piece`**: Base class for all chess pieces
- **`Position`**: Represents board coordinates

### Algorithms
- **Move Generation**: Each piece implements its own move logic
- **Check Detection**: Ray-casting from king position
- **Pinning Logic**: Pieces can be pinned to protect the king
- **Checkmate Detection**: Simulates all possible moves to find legal ones

## 🎨 Customization

### Adding New Features
- **New Piece Types**: Extend the `Piece` class
- **Custom Rules**: Modify the `Board` class
- **UI Enhancements**: Update the `ChessApp` class

### Modifying Assets
- **Piece Images**: Replace files in `Images/` directory
- **Board Theme**: Modify `grid.png` and `homeBackground.png`
- **Icons**: Update `icon.png` and `icon.ico`

## 🐛 Troubleshooting

### Common Issues
1. **"Java not found"**: Install Java 11+ and add to PATH
2. **"JavaFX not found"**: The javafx folder is included - ensure it's in the project root
3. **"Permission denied"**: Run as administrator or check file permissions

### Performance
- The game is optimized for smooth gameplay
- Move calculation is efficient with caching
- UI updates are minimal and targeted

## 📝 Development Notes

### Code Quality
- **Clean Architecture**: Well-organized class structure
- **Comprehensive Logic**: All chess rules implemented


### Future Enhancements
- **AI Opponent**: Add computer player
- **Move History**: Track and display move history
- **Save/Load**: Game state persistence
- **Network Play**: Multiplayer over network
- **Analysis Tools**: Move validation and hints

## 📄 License

This project is developed as a personal chess implementation. Feel free to use, modify, and distribute according to your needs.

## 👨‍💻 Author

**Md Faisal Aftab** - Chess Game Developer

---

**Enjoy playing chess!** ♟️ 
