package data;

public enum TileType {

 Air("air", true, false, 15), 
 Grass("grass", true, false, 0), 
 Grass2("grass2", true, false, 0), 
 Water("water", false, false, 5), 
 Desert("desert", true, false, 0), 
 Castle("castle", false, true, 0), 
 CastleVine("castle-vine", false, true, 0),
 Shrub("shrub", false, true, 0),
 CaveLeft("caveLeft", true, false, 5),
 CaveRight("caveRight", true, false, 5),
 
 //KenneyRPG stuff
 blank("blank", true, false, 0),
 rpgTile000("rpgTile000", true, false, 0), //Grass top-left corner
 rpgTile001("rpgTile001", true, false, 0), //Grass upper edge
 rpgTile002("rpgTile002", true, false,0), //Grass top-right corner
 rpgTile010("rpgTile010", false, false, 0), //Water top-left corner
 rpgTile011("rpgTile011", false, false, 0), //Water upper edge
 rpgTile012("rpgTile012", false, false, 0), //Water top-right corner
 rpgTile013("rpgTile013", false, false, 0), //Water with Grass edge piece
 rpgTile014("rpgTile014", false, false, 0),
 rpgTile018("rpgTile018", false, false, 0),
 rpgTile019("rpgTile019", false, false, 0),
 rpgTile020("rpgTile020", false, false, 0),
 rpgTile021("rpgTile021", false, false, 0),
 rpgTile022("rpgTile022", false, false, 0),
 rpgTile028("rpgTile028", false, false, 0),
 rpgTile029("rpgTile029", false, false, 0),
 rpgTile030("rpgTile030", false, false, 0),
 rpgTile031("rpgTile031", false, false, 0),
 rpgTile032("rpgTile032", false, false, 0),
 rpgTile036("rpgTile036", false, false, 0),
 rpgTile037("rpgTile037", false, false, 0),
 rpgTile038("rpgTile038", false, false, 0),
 rpgTile039("rpgTile039", false, false, 0), //Regular grass block
 rpgTilex39("rpgTile039", false, false, 0),
 rpgTile040("rpgTile040", false, false, 0), //Regular grass block
 rpgTilex40("rpgTile040", false, false, 0),
 rpgTile044("rpgTile044", false, false, 0),
 rpgTile045("rpgTile045", false, false, 0),
 rpgTile046("rpgTile046", false, false, 0),
 rpgTile057("rpgTile057", false, false, 0),
 rpgTile059("rpgTile059", false, false, 0),
 rpgTile060("rpgTile060", false, false, 0),
 rpgTile061("rpgTile061", false, false, 0),
 rpgTile062("rpgTile062", false, false, 0),
 rpgTile063("rpgTile063", false, false, 0),
 rpgTile064("rpgTile064", false, false, 0),
 rpgTile172("rpgTile172", false, false, 0), //Window
 rpgTile175("rpgTile175", false, false, 0),
 rpgTile177("rpgTile177", false, false, 0),
 rpgTile179("rpgTile179", false, false, 0),
 rpgTile195("rpgTile195", false, false, 0),
 rpgTile197("rpgTile197", false, false, 0),
 rpgTile199("rpgTile199", false, false, 0),
 
 //Menu stuff
 menuWhite("menuWhite", false, false, 0),
 menuBlack("menuBlack", false, false, 0);
 
 String textureName;
 boolean walkable;
 boolean shootable;
 int speed;

 TileType(String textureName, boolean walkable, boolean shootable, int speed) { // Can add future types
  this.textureName = textureName;
  this.walkable = walkable;
  this.shootable = shootable;
  this.speed = speed;
 }
}
