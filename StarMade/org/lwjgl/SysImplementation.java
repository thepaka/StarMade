package org.lwjgl;

abstract interface SysImplementation
{
  public abstract int getRequiredJNIVersion();

  public abstract int getJNIVersion();

  public abstract int getPointerSize();

  public abstract void setDebug(boolean paramBoolean);

  public abstract long getTimerResolution();

  public abstract long getTime();

  public abstract void alert(String paramString1, String paramString2);

  public abstract boolean openURL(String paramString);

  public abstract String getClipboard();

  public abstract boolean has64Bit();
}

/* Location:           C:\Users\Raul\Desktop\StarMade\StarMade.jar
 * Qualified Name:     org.lwjgl.SysImplementation
 * JD-Core Version:    0.6.2
 */