// Near Infinity - An Infinity Engine Browser and Editor
// Copyright (C) 2001 - 2005 Jon Olav Hauglid
// See LICENSE.txt for license information

package infinity.resource.nwn.gff.field;

import infinity.util.*;

import java.io.OutputStream;
import java.io.IOException;
import java.util.List;

public final class GffWord extends GffField
{
  private int value;

  public GffWord(byte buffer[], int fieldOffset, int labelOffset)
  {
    super(buffer, fieldOffset, labelOffset);
    value = Byteconvert.convertUnsignedShort(buffer, fieldOffset + 8);
  }

  public String toString()
  {
    return getLabel() + " = " + value;
  }

  public void compare(GffField field)
  {
    if (!getLabel().equals(field.getLabel()) ||
        value != ((GffWord)field).value)
      throw new IllegalStateException(toString() + " - " + field.toString());
  }

  public Object getValue()
  {
    return new Integer(value);
  }

  public int writeField(OutputStream os, List<String> labels, byte[] fieldData, int fieldDataIndex) throws IOException
  {
    Filewriter.writeInt(os, 2);
    Filewriter.writeInt(os, labels.indexOf(getLabel()));
    if (value > 32767)
      value -= 65536;
    byte v[] = Byteconvert.convertBack((short)value);
    Filewriter.writeBytes(os, new byte[] { v[0], v[1], 0, 0 });
    return fieldDataIndex;
  }
}

