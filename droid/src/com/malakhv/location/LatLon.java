/* *
 * Copyright (C) 2018 Mikhail Malakhov <malakhv@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * */

package com.malakhv.location;

import com.malakhv.util.StrUtils;
import com.malakhv.util.XmlUtils;

import java.util.List;
import java.util.Locale;

/**
 * Since in different map system/different API we may have a different structures to store
 * location data, we want to have our own to bring everything to a common denominator.
 *
 * @author <a href="https://github.com/malakhv">Mikhail.Malakhov</a>
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class LatLon {

    /** Radius of Earth, in meters. */
    public static final double EARTH_RADIUS = 6372795;

    /** The suffix for north latitude. */
    public static final String NORTH_LATITUDE_SUFFIX = "N";

    /** The suffix for south latitude. */
    public static final String SOUTH_LATITUDE_SUFFIX = "S";

    /** The suffix for east longitude. */
    public static final String EAST_LONGITUDE_SUFFIX = "E";

    /** The suffix for west longitude. */
    public static final String WEST_LONGITUDE_SUFFIX = "W";

    /** The minimal valid latitude value. */
    private static final double MIN_LATITUDE = -90.0D;

    /** The maximal valid latitude value. */
    private static final double MAX_LATITUDE = 90.0D;

    /** The minimal valid longitude value. */
    private static final double MIN_LONGITUDE = -180.0D;

    /** The maximal valid longitude value. */
    private static final double MAX_LONGITUDE = 180.0D;

    /**
     * The default delimiter that using to convert location data to/from string.
     * <p><b>Attention!</b> Please keep it sync with your external data format.</p>
     * */
    private static final String DEF_LOCATION_SEPARATOR = StrUtils.CHAR_COMMA;

    /** The format to represent location coordinate as string. */
    private static final String DEF_LOCATION_STRING_FORMAT = "%.7f";

    /**
     * The location data value (for latitude and/or longitude) when we have no correct values.
     * */
    private static final double WRONG_LOCATION = 512D; // Na GlaZ!

    /** The location title, if exists. */
    private String mTitle = null;

    /**
     * The latitude in WGS84 format (for example 50.0835494).
     * */
    private double mLat;

    /**
     * The longitude in WGS84 format (for example 14.4341414).
     * */
    private double mLon;

    /**
     * Construct a new {@link LatLon} instance with default parameters.
     * @see #hasLat()
     * @see #hasLon()
     * */
    public LatLon() {
        this(WRONG_LOCATION, WRONG_LOCATION);
    }

    /**
     * Construct a new {@link LatLon} instance with specified parameters.
     * */
    public LatLon(double lat, double lon) {
        mLat = lat; mLon = lon;
    }

    /**
     * Clears data in this object.
     * */
    public void clear() {
        mLat = mLon = WRONG_LOCATION;
    }

    /**
     * @return The latitude of this POI, in degrees.
     * */
    public double getLatitude() { return mLat; }

    /**
     * @return The longitude of this POI, in degrees.
     * */
    public double getLongitude() { return mLon; }

    /**
     * @return True, if this object has no valid location data.
     * */
    public boolean isEmpty() {
        return !(hasLat() && hasLon());
    }

    /**
     * @return True, if this object has valid location data.
     * */
    public boolean isValid() {
        return hasLat() && hasLon();
    }

    /**
     * @return True, if this object has valid latitude value.
     * */
    public boolean hasLat() {
        return LatLon.isValidLatitude(getLatitude());
    }

    /**
     * @return True, if this object has valid longitude value.
     * */
    public boolean hasLon() {
        return LatLon.isValidLongitude(getLongitude());
    }

    /**
     * Sets the title for this location object.
     * */
    public void setTitle(String title) {
        mTitle = title;
    }

    /**
     * Returns title of this location object, or {@code null};
     * */
    public String getTitle() {
        return mTitle;
    }

    /**
     * Obtains value from another {@link LatLon} object.
     * */
    public void obtain(LatLon origin) {
        if (origin == null) return;
        mLat = origin.getLatitude();
        mLon = origin.getLongitude();
        mTitle = origin.getTitle();
    }

    /**
     * Represents current latitude value as string in default format.
     * @see #latToStr(String)
     * */
    public String latToStr() {
        return latToStr(DEF_LOCATION_STRING_FORMAT);
    }

    /**
     * Represents current latitude value as string in specified {@code format}.
     * @see #latToStr()
     * */
    public String latToStr(String format) {
        return String.format(Locale.US, format, getLatitude());
    }

    /**
     * Represents current longitude value as string in default format.
     * @see #lonToStr(String)
     * */
    public String lonToStr() {
        return lonToStr(DEF_LOCATION_STRING_FORMAT);
    }

    /**
     * Represents current longitude value as string in specified {@code format}.
     * @see #lonToStr()
     * */
    public String lonToStr(String format) {
        return String.format(Locale.US, format, getLongitude());
    }

    /**
     * Represents location data as string. This method returns only latitude and longitude data.
     * */
    public String toString() {
        return toString(DEF_LOCATION_SEPARATOR);
    }

    /**
     * Represents location data as string with specified {@code separator} between
     * latitude and longitude. This method returns only latitude and longitude data.
     * */
    public String toString(String separator) {
        return latToStr() + separator + lonToStr();
    }

    /**
     * Represents location data as string in specified {@code format} and with
     * specified {@code separator} between latitude and longitude. This method returns only
     * latitude and longitude data.
     * */
    public String toString(String separator, String format) {
        return latToStr(format) + separator + lonToStr(format);
    }

    /**
     * Retrieves {@link LatLon} from string. Cannot be {@code null}, but can contains wrong data.
     *
     * @see #fromString(String, String)
     * @see #DEF_LOCATION_SEPARATOR
     * */
    public static LatLon fromString(String value) {
        return fromString(value, DEF_LOCATION_SEPARATOR);
    }

    /**
     * Retrieves {@link LatLon} from string. Cannot be {@code null}, but can contains wrong data.
     * @param value The string that contains latitude and longitude data
     * @param separator The separator between latitude and longitude in source string
     *
     * @see #fromString(String)
     * @see #DEF_LOCATION_SEPARATOR
     * */
    public static LatLon fromString(String value, String separator) {
        final LatLon obj = new LatLon();

        // Check input value
        if (StrUtils.isEmpty(value) || !value.contains(separator)) {
            return obj;
        }
        final String[] splitData = value.split(separator, 2);
        if (splitData.length != 2) return obj;

        // Parse location
        double lat, lon;
        try {
            lat = Double.parseDouble(splitData[0]);
            lon = Double.parseDouble(splitData[1]);
        } catch (NumberFormatException e) {
            return obj;
        }
        obj.mLat = lat; obj.mLon = lon;

        return obj;
    }

    /**
     * Checks latitude value.
     * @return True, if specified latitude value is valid, otherwise false.
     * */
    private static boolean isValidLatitude(double value) {
        return value >= MIN_LATITUDE && value <= MAX_LATITUDE;
    }

    /**
     * Checks longitude value.
     * @return True, if specified longitude value is valid, otherwise false.
     * */
    private static boolean isValidLongitude(double value) {
        return value >= MIN_LONGITUDE && value <= MAX_LONGITUDE;
    }

    /*----------------------------------------------------------------------------------------*/
    /* Distance
    /*----------------------------------------------------------------------------------------*/

    /**
     * @return The minimum distance between two points (by haversine formula), in meters.
     * */
    public static double getDistance(LatLon a, LatLon b) {
        double distance = 0;

        // Check input parameters
        if (!a.isValid() || !b.isValid()) return distance;

        // Convert to radians
        final double lat1 = Math.toRadians(a.getLatitude());
        final double lon1 = Math.toRadians(a.getLongitude());
        final double lat2 = Math.toRadians(b.getLatitude());
        final double lon2 = Math.toRadians(b.getLongitude());

        // Cos and Sin of latitudes
        final double cosLat1 = Math.cos(lat1);
        final double cosLat2 = Math.cos(lat2);
        final double sinLat1 = Math.sin(lat1);
        final double sinLat2 = Math.sin(lat2);

        // Longitudes delta
        final double delta = lon2 - lon1;
        final double cosDelta = Math.cos(delta);
        final double sinDelta = Math.sin(delta);

        // The length of a large circle
        final double tmp = cosLat1 * sinLat2 - sinLat1 * cosLat2 * cosDelta;
        double y = Math.sqrt(Math.pow(cosLat2 * sinDelta, 2)
                + Math.pow(tmp, 2));
        double x =  sinLat1 * sinLat2 + cosLat1 * cosLat2 * cosDelta;
        distance = Math.atan2(y, x) * EARTH_RADIUS;

        return distance;
    }

    /*----------------------------------------------------------------------------------------*/
    /* Export to XML
    /*----------------------------------------------------------------------------------------*/

    /** The default root tag in xml document. */
    private static final String DEF_XML_DOC_TAG = "locations";

    /** The default tag for each location item. */
    private static final String DEF_XML_ITEM_TAG = "location";

    private static final String DEF_XML_ITEM_PARAM_LAT = "lat";
    private static final String DEF_XML_ITEM_PARAM_LON = "lon";
    private static final String DEF_XML_ITEM_PARAM_TITLE = "title";

    /**
     * Represents current location data as xml string in format:<br>
     * {@code <location lat="VALUE" lon="VALUE" >}
     * */
    public String toXmlString() {
        return toXmlString(DEF_XML_ITEM_TAG);
    }

    /**
     * Represents current location data as xml string in format:<br>
     * {@code <tag lat="VALUE" lon="VALUE" >}<br>
     * where {@code tag} is specified tag value.
     * */
    public String toXmlString(String tag) {
        return LatLon.toXmlString(tag, this);
    }

    /**
     * Represents specified location item as xml string in format:<br>
     * {@code <tag lat="VALUE" lon="VALUE" >}<br>
     * where {@code tag} is specified tag value.
     * */
    public static String toXmlString(String itemTag, LatLon item) {
        if (StrUtils.isEmpty(itemTag) || item == null) return StrUtils.EMPTY;

        // Start tag
        final StringBuilder builder = new StringBuilder();
        builder.append("<").append(itemTag).append(StrUtils.CHAR_SPACE);

        // Title, may be empty
        builder.append(XmlUtils.makeNameValuePair(DEF_XML_ITEM_PARAM_TITLE,
                item.mTitle)).append(StrUtils.CHAR_SPACE);

        // Latitude, if exists
        if (item.hasLat()) {
            builder.append(XmlUtils.makeNameValuePair(DEF_XML_ITEM_PARAM_LAT,
                    item.latToStr()));
            builder.append(StrUtils.CHAR_SPACE);
        }

        // Longitude, if exists
        if (item.hasLon()) {
            builder.append(XmlUtils.makeNameValuePair(DEF_XML_ITEM_PARAM_LON,
                    item.lonToStr()));
        }

        // End tag
        builder.append(StrUtils.CHAR_SPACE).append("/>");
        return builder.toString();
    }

    /**
     * Represents specified locations as xml document.
     * @see #toXmlString(String, LatLon)
     * */
    public static String toXml(List<LatLon> data) {
        return LatLon.toXml(DEF_XML_DOC_TAG, DEF_XML_ITEM_TAG, data);
    }

    /**
     * Represents specified locations as xml document.
     * @see #toXmlString(String, LatLon)
     * */
    public static String toXml(String docTag, String itemTag, List<LatLon> data) {
        if (StrUtils.isEmpty(docTag) || StrUtils.isEmpty(itemTag)) return  StrUtils.EMPTY;
        final String item_tab = "    ";

        // Start xml doc
        final StringBuilder builder = new StringBuilder();
        builder.append(XmlUtils.XML_DOC_HEAD).append(StrUtils.CHAR_NEW_LINE);
        builder.append(XmlUtils.XML_TAG_START).append(docTag).append(XmlUtils.XML_TAG_END)
                .append(StrUtils.CHAR_NEW_LINE);

        // Doc data. We want to return empty document even if data is null or empty
        if (data != null && data.size() > 0) {
            for (LatLon item : data) {
                builder.append(item_tab).append(LatLon.toXmlString(itemTag, item));
                builder.append(StrUtils.CHAR_NEW_LINE);
            }
        }

        // End xml doc
        builder.append(XmlUtils.XML_TAG_START).append(StrUtils.CHAR_SLASH).append(docTag)
                .append(XmlUtils.XML_TAG_END);
        return builder.toString();
    }

}