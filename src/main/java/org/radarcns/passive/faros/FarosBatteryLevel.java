/**
 * Autogenerated by Avro
 *
 * DO NOT EDIT DIRECTLY
 */
package org.radarcns.passive.faros;

import org.apache.avro.specific.SpecificData;
import org.apache.avro.message.BinaryMessageEncoder;
import org.apache.avro.message.BinaryMessageDecoder;
import org.apache.avro.message.SchemaStore;

@SuppressWarnings("all")
/** Device battery level. */
@org.apache.avro.specific.AvroGenerated
public class FarosBatteryLevel extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  private static final long serialVersionUID = -9060167672742540253L;
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"FarosBatteryLevel\",\"namespace\":\"org.radarcns.passive.faros\",\"doc\":\"Device battery level.\",\"fields\":[{\"name\":\"time\",\"type\":\"double\",\"doc\":\"Device timestamp in UTC (s).\"},{\"name\":\"timeReceived\",\"type\":\"double\",\"doc\":\"Device receiver timestamp in UTC (s).\"},{\"name\":\"batteryLevel\",\"type\":\"float\",\"doc\":\"Battery level from 0 to 1. Note that the battery level is a rough estimate.\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }

  private static SpecificData MODEL$ = new SpecificData();

  private static final BinaryMessageEncoder<FarosBatteryLevel> ENCODER =
      new BinaryMessageEncoder<FarosBatteryLevel>(MODEL$, SCHEMA$);

  private static final BinaryMessageDecoder<FarosBatteryLevel> DECODER =
      new BinaryMessageDecoder<FarosBatteryLevel>(MODEL$, SCHEMA$);

  /**
   * Return the BinaryMessageDecoder instance used by this class.
   */
  public static BinaryMessageDecoder<FarosBatteryLevel> getDecoder() {
    return DECODER;
  }

  /**
   * Create a new BinaryMessageDecoder instance for this class that uses the specified {@link SchemaStore}.
   * @param resolver a {@link SchemaStore} used to find schemas by fingerprint
   */
  public static BinaryMessageDecoder<FarosBatteryLevel> createDecoder(SchemaStore resolver) {
    return new BinaryMessageDecoder<FarosBatteryLevel>(MODEL$, SCHEMA$, resolver);
  }

  /** Serializes this FarosBatteryLevel to a ByteBuffer. */
  public java.nio.ByteBuffer toByteBuffer() throws java.io.IOException {
    return ENCODER.encode(this);
  }

  /** Deserializes a FarosBatteryLevel from a ByteBuffer. */
  public static FarosBatteryLevel fromByteBuffer(
      java.nio.ByteBuffer b) throws java.io.IOException {
    return DECODER.decode(b);
  }

  /** Device timestamp in UTC (s). */
  @Deprecated public double time;
  /** Device receiver timestamp in UTC (s). */
  @Deprecated public double timeReceived;
  /** Battery level from 0 to 1. Note that the battery level is a rough estimate. */
  @Deprecated public float batteryLevel;

  /**
   * Default constructor.  Note that this does not initialize fields
   * to their default values from the schema.  If that is desired then
   * one should use <code>newBuilder()</code>.
   */
  public FarosBatteryLevel() {}

  /**
   * All-args constructor.
   * @param time Device timestamp in UTC (s).
   * @param timeReceived Device receiver timestamp in UTC (s).
   * @param batteryLevel Battery level from 0 to 1. Note that the battery level is a rough estimate.
   */
  public FarosBatteryLevel(java.lang.Double time, java.lang.Double timeReceived, java.lang.Float batteryLevel) {
    this.time = time;
    this.timeReceived = timeReceived;
    this.batteryLevel = batteryLevel;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call.
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return time;
    case 1: return timeReceived;
    case 2: return batteryLevel;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  // Used by DatumReader.  Applications should not call.
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: time = (java.lang.Double)value$; break;
    case 1: timeReceived = (java.lang.Double)value$; break;
    case 2: batteryLevel = (java.lang.Float)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'time' field.
   * @return Device timestamp in UTC (s).
   */
  public java.lang.Double getTime() {
    return time;
  }

  /**
   * Sets the value of the 'time' field.
   * Device timestamp in UTC (s).
   * @param value the value to set.
   */
  public void setTime(java.lang.Double value) {
    this.time = value;
  }

  /**
   * Gets the value of the 'timeReceived' field.
   * @return Device receiver timestamp in UTC (s).
   */
  public java.lang.Double getTimeReceived() {
    return timeReceived;
  }

  /**
   * Sets the value of the 'timeReceived' field.
   * Device receiver timestamp in UTC (s).
   * @param value the value to set.
   */
  public void setTimeReceived(java.lang.Double value) {
    this.timeReceived = value;
  }

  /**
   * Gets the value of the 'batteryLevel' field.
   * @return Battery level from 0 to 1. Note that the battery level is a rough estimate.
   */
  public java.lang.Float getBatteryLevel() {
    return batteryLevel;
  }

  /**
   * Sets the value of the 'batteryLevel' field.
   * Battery level from 0 to 1. Note that the battery level is a rough estimate.
   * @param value the value to set.
   */
  public void setBatteryLevel(java.lang.Float value) {
    this.batteryLevel = value;
  }

  /**
   * Creates a new FarosBatteryLevel RecordBuilder.
   * @return A new FarosBatteryLevel RecordBuilder
   */
  public static org.radarcns.passive.faros.FarosBatteryLevel.Builder newBuilder() {
    return new org.radarcns.passive.faros.FarosBatteryLevel.Builder();
  }

  /**
   * Creates a new FarosBatteryLevel RecordBuilder by copying an existing Builder.
   * @param other The existing builder to copy.
   * @return A new FarosBatteryLevel RecordBuilder
   */
  public static org.radarcns.passive.faros.FarosBatteryLevel.Builder newBuilder(org.radarcns.passive.faros.FarosBatteryLevel.Builder other) {
    return new org.radarcns.passive.faros.FarosBatteryLevel.Builder(other);
  }

  /**
   * Creates a new FarosBatteryLevel RecordBuilder by copying an existing FarosBatteryLevel instance.
   * @param other The existing instance to copy.
   * @return A new FarosBatteryLevel RecordBuilder
   */
  public static org.radarcns.passive.faros.FarosBatteryLevel.Builder newBuilder(org.radarcns.passive.faros.FarosBatteryLevel other) {
    return new org.radarcns.passive.faros.FarosBatteryLevel.Builder(other);
  }

  /**
   * RecordBuilder for FarosBatteryLevel instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<FarosBatteryLevel>
    implements org.apache.avro.data.RecordBuilder<FarosBatteryLevel> {

    /** Device timestamp in UTC (s). */
    private double time;
    /** Device receiver timestamp in UTC (s). */
    private double timeReceived;
    /** Battery level from 0 to 1. Note that the battery level is a rough estimate. */
    private float batteryLevel;

    /** Creates a new Builder */
    private Builder() {
      super(SCHEMA$);
    }

    /**
     * Creates a Builder by copying an existing Builder.
     * @param other The existing Builder to copy.
     */
    private Builder(org.radarcns.passive.faros.FarosBatteryLevel.Builder other) {
      super(other);
      if (isValidValue(fields()[0], other.time)) {
        this.time = data().deepCopy(fields()[0].schema(), other.time);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.timeReceived)) {
        this.timeReceived = data().deepCopy(fields()[1].schema(), other.timeReceived);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.batteryLevel)) {
        this.batteryLevel = data().deepCopy(fields()[2].schema(), other.batteryLevel);
        fieldSetFlags()[2] = true;
      }
    }

    /**
     * Creates a Builder by copying an existing FarosBatteryLevel instance
     * @param other The existing instance to copy.
     */
    private Builder(org.radarcns.passive.faros.FarosBatteryLevel other) {
            super(SCHEMA$);
      if (isValidValue(fields()[0], other.time)) {
        this.time = data().deepCopy(fields()[0].schema(), other.time);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.timeReceived)) {
        this.timeReceived = data().deepCopy(fields()[1].schema(), other.timeReceived);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.batteryLevel)) {
        this.batteryLevel = data().deepCopy(fields()[2].schema(), other.batteryLevel);
        fieldSetFlags()[2] = true;
      }
    }

    /**
      * Gets the value of the 'time' field.
      * Device timestamp in UTC (s).
      * @return The value.
      */
    public java.lang.Double getTime() {
      return time;
    }

    /**
      * Sets the value of the 'time' field.
      * Device timestamp in UTC (s).
      * @param value The value of 'time'.
      * @return This builder.
      */
    public org.radarcns.passive.faros.FarosBatteryLevel.Builder setTime(double value) {
      validate(fields()[0], value);
      this.time = value;
      fieldSetFlags()[0] = true;
      return this;
    }

    /**
      * Checks whether the 'time' field has been set.
      * Device timestamp in UTC (s).
      * @return True if the 'time' field has been set, false otherwise.
      */
    public boolean hasTime() {
      return fieldSetFlags()[0];
    }


    /**
      * Clears the value of the 'time' field.
      * Device timestamp in UTC (s).
      * @return This builder.
      */
    public org.radarcns.passive.faros.FarosBatteryLevel.Builder clearTime() {
      fieldSetFlags()[0] = false;
      return this;
    }

    /**
      * Gets the value of the 'timeReceived' field.
      * Device receiver timestamp in UTC (s).
      * @return The value.
      */
    public java.lang.Double getTimeReceived() {
      return timeReceived;
    }

    /**
      * Sets the value of the 'timeReceived' field.
      * Device receiver timestamp in UTC (s).
      * @param value The value of 'timeReceived'.
      * @return This builder.
      */
    public org.radarcns.passive.faros.FarosBatteryLevel.Builder setTimeReceived(double value) {
      validate(fields()[1], value);
      this.timeReceived = value;
      fieldSetFlags()[1] = true;
      return this;
    }

    /**
      * Checks whether the 'timeReceived' field has been set.
      * Device receiver timestamp in UTC (s).
      * @return True if the 'timeReceived' field has been set, false otherwise.
      */
    public boolean hasTimeReceived() {
      return fieldSetFlags()[1];
    }


    /**
      * Clears the value of the 'timeReceived' field.
      * Device receiver timestamp in UTC (s).
      * @return This builder.
      */
    public org.radarcns.passive.faros.FarosBatteryLevel.Builder clearTimeReceived() {
      fieldSetFlags()[1] = false;
      return this;
    }

    /**
      * Gets the value of the 'batteryLevel' field.
      * Battery level from 0 to 1. Note that the battery level is a rough estimate.
      * @return The value.
      */
    public java.lang.Float getBatteryLevel() {
      return batteryLevel;
    }

    /**
      * Sets the value of the 'batteryLevel' field.
      * Battery level from 0 to 1. Note that the battery level is a rough estimate.
      * @param value The value of 'batteryLevel'.
      * @return This builder.
      */
    public org.radarcns.passive.faros.FarosBatteryLevel.Builder setBatteryLevel(float value) {
      validate(fields()[2], value);
      this.batteryLevel = value;
      fieldSetFlags()[2] = true;
      return this;
    }

    /**
      * Checks whether the 'batteryLevel' field has been set.
      * Battery level from 0 to 1. Note that the battery level is a rough estimate.
      * @return True if the 'batteryLevel' field has been set, false otherwise.
      */
    public boolean hasBatteryLevel() {
      return fieldSetFlags()[2];
    }


    /**
      * Clears the value of the 'batteryLevel' field.
      * Battery level from 0 to 1. Note that the battery level is a rough estimate.
      * @return This builder.
      */
    public org.radarcns.passive.faros.FarosBatteryLevel.Builder clearBatteryLevel() {
      fieldSetFlags()[2] = false;
      return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public FarosBatteryLevel build() {
      try {
        FarosBatteryLevel record = new FarosBatteryLevel();
        record.time = fieldSetFlags()[0] ? this.time : (java.lang.Double) defaultValue(fields()[0]);
        record.timeReceived = fieldSetFlags()[1] ? this.timeReceived : (java.lang.Double) defaultValue(fields()[1]);
        record.batteryLevel = fieldSetFlags()[2] ? this.batteryLevel : (java.lang.Float) defaultValue(fields()[2]);
        return record;
      } catch (java.lang.Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumWriter<FarosBatteryLevel>
    WRITER$ = (org.apache.avro.io.DatumWriter<FarosBatteryLevel>)MODEL$.createDatumWriter(SCHEMA$);

  @Override public void writeExternal(java.io.ObjectOutput out)
    throws java.io.IOException {
    WRITER$.write(this, SpecificData.getEncoder(out));
  }

  @SuppressWarnings("unchecked")
  private static final org.apache.avro.io.DatumReader<FarosBatteryLevel>
    READER$ = (org.apache.avro.io.DatumReader<FarosBatteryLevel>)MODEL$.createDatumReader(SCHEMA$);

  @Override public void readExternal(java.io.ObjectInput in)
    throws java.io.IOException {
    READER$.read(this, SpecificData.getDecoder(in));
  }

}
