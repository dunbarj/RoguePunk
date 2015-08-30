package data;

import org.newdawn.slick.opengl.Texture;

import static helpers.Artist.*;

public class Tile {

 public static float shiftX, shiftY;
 private float x, y, width, height;
 private Texture texture;
 private TileType type;

 public Tile(float x, float y, float width, float height, TileType type, String use) {
  this.x = x;
  this.y = y;
  this.width = width;
  this.height = height;
  this.type = type;
  if (use.equals("map")) {
	  this.texture = QuickLoadMap(type.textureName); //Changed for new map style
  }
  else if (use.equals("menu")) {
  	this.texture = QuickLoadMenu(type.textureName); //Changed for new map style
  }
 }

 public float[] getCenter() {
	 float[] array = new float[2];
	 array[0] = getX() + (getWidth() / 2);
	 array[1] = getY() + (getHeight() / 2);
	 return array;
 }
 
 public void Draw() {
  DrawQuadTex(texture, x, y, width, height);
 }

 public float getX() {
  return x + shiftX;
 }

 public int getXPlace() {
  return (int) x / 64;
 }

 public int getYPlace() {
  return (int) y / 64;
 }

 public void setX(float x) {
  this.x = x;
 }

 public float getY() {
  return y + shiftY;
 }

 public void setY(float y) {
  this.y = y;
 }

 public float getWidth() {
  return width;
 }

 public void setWidth(float width) {
  this.width = width;
 }

 public float getHeight() {
  return height;
 }

 public void setHeight(float height) {
  this.height = height;
 }

 public Texture getTexture() {
  return texture;
 }

 public void setTexture(Texture texture) {
  this.texture = texture;
 }

 public TileType getType() {
  return type;
 }

 public void setType(TileType type) {
  this.type = type;
 }
}
