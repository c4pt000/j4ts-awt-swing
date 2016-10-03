package java.awt;

import static jsweet.util.Globals.any;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import jsweet.dom.HTMLElement;

public abstract class Component implements HTMLComponent {

	public static final float TOP_ALIGNMENT = 0.0f;

	public static final float CENTER_ALIGNMENT = 0.5f;

	public static final float BOTTOM_ALIGNMENT = 1.0f;

	public static final float LEFT_ALIGNMENT = 0.0f;

	public static final float RIGHT_ALIGNMENT = 1.0f;

	protected HTMLElement htmlElement;

	boolean enabled;

	boolean valid;

	Color background;

	Color foreground;

	Font font;

	boolean visible;

	String name;

	Integer x, y, width, height;

	@Override
	public final void bindHTML(HTMLElement htmlElement) {
		if (this.htmlElement != null) {
			throw new RuntimeException("already bound");
		}
		this.htmlElement = htmlElement;
		initHTML();
	}

	@Override	
	public HTMLElement getHTMLElement() {
		if (htmlElement == null) {
			initHTML();
		}
		return any(htmlElement);
	}

	@Override
	public void initHTML() {
		if (htmlElement == null) {
			createHTML();
		}
		htmlElement.id = "cmp" + Component.CURRENT_ID++;
		if (background != null) {
			htmlElement.style.backgroundColor = background.toHTML();
		}
		if (width != null) {
			htmlElement.style.width = width + "px";
		} else {
			htmlElement.style.width = "auto";
		}
		if (height != null) {
			htmlElement.style.height = height + "px";
		} else {
			htmlElement.style.height = "auto";
		}
	}

	public Point getLocationOnScreen() {
		return new Point(getX(), getY());
	}

	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}

	public int getWidth() {
		return (int) htmlElement.offsetWidth;
	}

	public int getHeight() {
		return (int) htmlElement.offsetHeight;
	}

	public int getX() {
		return (int) htmlElement.offsetLeft;
	}

	public int getY() {
		return (int) htmlElement.offsetTop;
	}

	public void setSize(int width, int height) {
		this.width = width;
		this.height = height;
		if (htmlElement != null) {
			htmlElement.style.width = width + "px";
			htmlElement.style.height = height + "px";
		}
	}

	public void setSize(Dimension d) {
		setSize(d.width, d.height);
	}

	private PropertyChangeSupport changeSupport;

	public static int CURRENT_ID = 0;

	public PropertyChangeListener[] getPropertyChangeListeners(String propertyName) {
		if (changeSupport == null) {
			return new PropertyChangeListener[0];
		}
		return changeSupport.getPropertyChangeListeners(propertyName);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (changeSupport == null) {
			changeSupport = new PropertyChangeSupport(this);
		}
		changeSupport.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		if (listener == null || changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener(listener);
	}

	public PropertyChangeListener[] getPropertyChangeListeners() {
		if (changeSupport == null) {
			return new PropertyChangeListener[0];
		}
		return changeSupport.getPropertyChangeListeners();
	}

	public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		if (changeSupport == null) {
			changeSupport = new PropertyChangeSupport(this);
		}
		changeSupport.addPropertyChangeListener(propertyName, listener);
	}

	public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
		if (listener == null || changeSupport == null) {
			return;
		}
		changeSupport.removePropertyChangeListener(propertyName, listener);
	}

	protected void firePropertyChange(String propertyName, Object oldValue, Object newValue) {
		PropertyChangeSupport changeSupport;
		changeSupport = this.changeSupport;
		if (changeSupport == null || (oldValue != null && newValue != null && oldValue.equals(newValue))) {
			return;
		}
		changeSupport.firePropertyChange(propertyName, oldValue, newValue);
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
		if (htmlElement != null) {
			htmlElement.style.backgroundColor = background.toHTML();
		}
	}

	public Color getForeground() {
		return foreground;
	}

	public void setForeground(Color foreground) {
		this.foreground = foreground;
		if (htmlElement != null) {
			htmlElement.style.color = foreground.toHTML();
		}
	}

	public Font getFont() {
		return font;
	}

	public void setFont(Font font) {
		this.font = font;
		if (htmlElement != null) {
			htmlElement.style.font = font.toHTML();
		}
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
		if (htmlElement != null) {
			htmlElement.style.display = "none";
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	protected String paramString() {
		String thisName = getName();
		String str = (thisName != null ? thisName : "") + "," + getX() + "," + getY() + "," + getWidth() + "x"
				+ getHeight();
		if (!isValid()) {
			str += ",invalid";
		}
		if (!visible) {
			str += ",hidden";
		}
		if (!enabled) {
			str += ",disabled";
		}
		return str;
	}

	public boolean isValid() {
		return valid;
	}

	public void setValid(boolean valid) {
		this.valid = valid;
	}

	public void validate() {
	}

	public void paint(Graphics g) {
	}

	public void update(Graphics g) {
		paint(g);
	}

	public void paintAll(Graphics g) {
		paint(g);
	}

	public Graphics getGraphics() {
		return null;
	}

	public void doPaintInternal() {
		Graphics g = getGraphics();
		if (g != null) {
			paint(g);
		}
	}

}