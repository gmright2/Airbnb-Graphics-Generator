package Classes;

import com.restfb.Facebook;


public class PhotoTag {
	   @Facebook("tag_uid")
	   private String tagUid;

	   @Facebook("tag_text")
	   private String tagText;
	   
	   public PhotoTag(String id, String text){
		   this.tagUid = id;
		   this.tagText = text;
	   }
}