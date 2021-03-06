package uk.ac.soton.ecs.tdh1g19.ch6;

import org.openimaj.data.dataset.VFSGroupDataset;
import org.openimaj.data.dataset.VFSListDataset;
import org.openimaj.image.DisplayUtilities;
import org.openimaj.image.FImage;
import org.openimaj.image.ImageUtilities;
import org.openimaj.image.dataset.FlickrImageDataset;
import org.openimaj.util.api.auth.DefaultTokenFactory;
import org.openimaj.util.api.auth.common.FlickrAPIToken;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;

// Using datasets that store collections of images for processing
public class ImageDatasets {
    public static void main(String[] args) {
        try {
            VFSListDataset<FImage> images = new VFSListDataset<>(Paths.get("").toAbsolutePath() + "\\OpenIMAJ-Tutorials\\images", ImageUtilities.FIMAGE_READER);

            // Database of images can be treated like a list
            System.out.println(images.size());
            DisplayUtilities.display(images.getRandomInstance(), "A random image from the dataset");
            DisplayUtilities.display("My images", images);

            VFSListDataset<FImage> faces = new VFSListDataset<>("zip:http://datasets.openimaj.org/att_faces.zip", ImageUtilities.FIMAGE_READER);
            DisplayUtilities.display("ATT faces", faces);

            VFSGroupDataset<FImage> groupedFaces = new VFSGroupDataset<>("zip:http://datasets.openimaj.org/att_faces.zip", ImageUtilities.FIMAGE_READER);

            // Display a random photo of each person in the dataset - exercise 1
            ArrayList<FImage> randomFaces = new ArrayList<>();
            Random rand = new Random();

            // Display images for the same person
            for (final Map.Entry<String, VFSListDataset<FImage>> entry : groupedFaces.entrySet()) {
                DisplayUtilities.display(entry.getKey(), entry.getValue());
                randomFaces.add(entry.getValue().get(rand.nextInt(entry.getValue().size())));
            }
            DisplayUtilities.display("Random faces", randomFaces);

            // Use Flickr to display the results of an image search
            FlickrAPIToken flickrToken = DefaultTokenFactory.get(FlickrAPIToken.class);
            FlickrImageDataset<FImage> cats = FlickrImageDataset.create(ImageUtilities.FIMAGE_READER, flickrToken, "cat", 10);
            DisplayUtilities.display("Cats", cats);

            /*
            Flickr:
            Key: 6b14cc9c8fbe2de62597c8a05c11ade7
            Secret: 9f4560b39a9bb19b
             */

            /*
            Database sources:
            https://commons.apache.org/proper/commons-vfs/filesystems.html

            Databases can be built from a large number of sources including FTP, HTTP and Jar.
            Some of these sources allow for additional functionality like writing, creating, deleting files and many more which ZIP doesn't have which can only read.

            Exercises 3 and 4 could not be completed due to no freely accessible Bing Search API token
             */
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
