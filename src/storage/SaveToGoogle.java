/**
 * 
 */
package storage;

import java.io.IOException;
import java.util.Arrays;
//import java.util.ResourceBundle;

import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.ParentReference;

/**
 * @author leanne.miller
 *
 */
public class SaveToGoogle {


	/**
	   * Insert new file.
	   * Ref: https://developers.google.com/drive/v2/reference/files/insert
	   *
	   * @param service Drive API service instance.
	   * @param title Title of the file to insert, including the extension.
	   * @param description Description of the file to insert.
	   * @param parentId Optional parent folder's ID.
	   * @param mimeType MIME type of the file to insert.
	   * @param filename Filename of the file to insert.
	   * @return Inserted file metadata if successful, {@code null} otherwise.
	   */
	private static File insertFile(Drive service, String title, String description,
		      String parentId, String mimeType, String filename) {
		    // File's metadata.
		    File body = new File();
		    body.setTitle(title);
		    body.setDescription(description);
		    body.setMimeType(mimeType);

		    // Set the parent folder.
		    if (parentId != null && parentId.length() > 0) {
		      body.setParents(
		          Arrays.asList(new ParentReference().setId(parentId)));
		    }

		    // File's content.
		    java.io.File fileContent = new java.io.File(filename);
		    FileContent mediaContent = new FileContent(mimeType, fileContent);
		    try {
		      File file = service.files().insert(body, mediaContent).execute();

		      // Uncomment the following line to print the File ID.
		      // System.out.println("File ID: %s" + file.getId());

		      return file;
		    } catch (IOException e) {
		      System.out.println("An error occured: " + e);
		      return null;
		    }
		  }

	
}
