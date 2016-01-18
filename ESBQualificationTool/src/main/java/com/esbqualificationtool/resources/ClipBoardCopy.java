package com.esbqualificationtool.resources;

import java.awt.datatransfer.*;
import java.awt.*;
import java.io.*;

public final class ClipBoardCopy implements ClipboardOwner {
    
  

  public void lostOwnership(Clipboard cb, Transferable t) {
    System.out.println("Contenu modifié");
    }

  public void setClipboardContents(String s) {
    StringSelection ss = new StringSelection(s);
    Clipboard cb = Toolkit.getDefaultToolkit().getSystemClipboard();
    cb.setContents(ss, this);
    }

  public String getClipboardContents() {
    Transferable t = Toolkit.getDefaultToolkit().     getSystemClipboard().getContents(null);

    try {
      if (t != null && t.isDataFlavorSupported(DataFlavor.stringFlavor)) {
        String text = (String)t.getTransferData(DataFlavor.stringFlavor);
        return text;
        }
      }
    catch (UnsupportedFlavorException e) {
      System.out.println(e);
      }
    catch (IOException e) {
      System.out.println(e);
      }
    return null;
    }
  }
