/** @version $Id: File.java,v 1.10 2014/12/02 01:23:11 ist178942 Exp $ */
package poof;

/**
 * Class that represents the concept of a file. Has content.
 */
public class File extends Entry {

/*==============================================================================
 * Attributes
 *============================================================================*/

    /** The content of the file. */
    private String _content = "";

/*==============================================================================
 * Getter and Setters
 *============================================================================*/

    /**
     * @return _content.
     */
    public String getContent() {
        return _content;
    }

    /**
     * @param content
     *          the new content of the file.
     */
    public void setContent(String content) {
        _content = content;
    }

/*==============================================================================
 * Methods
 *============================================================================*/

    /**
     * Constructor.
     *
     * @param owner
     *          the owner of the file.
     * @param fileName
     *          the name of the file.
     * @param permission
     *          if the file is public or private.
     */
    public File(User owner, String fileName, boolean permission) {
        super(owner, fileName, permission);
    }

    /**
     * Append a line to the end of the file.
     *
     * @param content
     *          the content to append.
     */
    public void append(String content) {
        if (!content.equals("")) {
            _content += content + '\n';
        }
    }

    /**
     * The size of the file is equal to the number of chars in _content.
     *
     * @return the size of the file.
     */
    @Override
    public int size() {
        return _content.length();
    }

    /**
     * @see java.lang.Object#equals(Object)
     */
    @Override
    public boolean equals(Object o) {
        if (o instanceof File) {
            File file = (File) o;
            return getContent().equals(file.getContent()) && super.equals(file);
        }
        return false;
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "- " + super.toString();
    }

}
