package com.mygdx.game.Map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Json;
import com.mygdx.game.Utility;
import com.mygdx.game.Battle.BattleManager;

public abstract class Map {
    private static final String TAG = Map.class.getSimpleName();

    public final static float UNIT_SCALE  = 1/32f;

    //Map layers
    protected final static String MAP_COLLISION_LAYER = "items";
    protected final static String MAP_SPAWNS_LAYER = "Spawn points";
    protected final static String BACKGROUND_LAYER = "background";
    
    protected final static String PLAYER_START = "PLAYER_START";

    protected Json _json;
    protected TiledMap _currentMap = null;
    protected TiledMapStage tiledmapstage;
    protected BattleManager battlemanager;
    protected MapLayer _collisionLayer = null;
    protected MapLayer _spawnsLayer = null;

    protected MapFactory.MapType _currentMapType;
    
    //props
    protected MapProperties prop;

	protected int mapWidth;
    protected int mapHeight;
    protected int tilePixelWidth;
    protected int tilePixelHeight;

    Map( MapFactory.MapType mapType, String fullMapPath){
        _json = new Json();
        _currentMapType = mapType;

        if( fullMapPath == null || fullMapPath.isEmpty() ) {
            Gdx.app.debug(TAG, "Map is invalid");
            return;
        }

        if( _currentMap != null ){
            _currentMap.dispose();
            tiledmapstage.dispose();
        }

        Utility.loadMapAsset(fullMapPath);
        if( Utility.isAssetLoaded(fullMapPath) ) {
            _currentMap = Utility.getMapAsset(fullMapPath);
        }else{
            Gdx.app.debug(TAG, "Map not loaded");
            return;
        }
        
        //setup properties
        prop = _currentMap.getProperties();
        
		mapWidth = prop.get("width", Integer.class);
		mapHeight = prop.get("height", Integer.class);
		tilePixelWidth = prop.get("tilewidth", Integer.class);
		tilePixelHeight = prop.get("tileheight", Integer.class);
    }

    public MapLayer getCollisionLayer(){
        return _collisionLayer;
    }

    public TiledMap getCurrentTiledMap() {
        return _currentMap;
    }
    
    public int getMapWidth() {
		return mapWidth;
	}

	public void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	public int getMapHeight() {
		return mapHeight;
	}

	public void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}
    
}