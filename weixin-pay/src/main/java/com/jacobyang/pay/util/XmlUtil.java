package com.jacobyang.pay.util;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.naming.NameCoder;
import com.thoughtworks.xstream.io.naming.NoNameCoder;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;

import java.io.Writer;
import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * created on 2018-01-17 10:59
 *
 * @author nextyu
 */
public final class XmlUtil {
    private XmlUtil() {
    }

    private static final XStream XSTREAM = new XStream(new XppDriverWithCDATA("UTF_8", new NoNameCoder()));

    static {
        XSTREAM.registerConverter(new MapEntryConverter());
        XSTREAM.alias("xml", Map.class);
        XSTREAM.alias("xml", TreeMap.class);
    }

    public static String map2Xml(Map<String, String> map) {
        return XSTREAM.toXML(map);
    }

    public static Map<String, String> xml2Map(String xml) {
        return (Map<String, String>) XSTREAM.fromXML(xml);
    }


    private static class MapEntryConverter implements Converter {

        @Override
        public boolean canConvert(Class clazz) {
            return AbstractMap.class.isAssignableFrom(clazz);
        }

        @Override
        public void marshal(Object value, HierarchicalStreamWriter writer, MarshallingContext context) {

            AbstractMap map = (AbstractMap) value;
            for (Object obj : map.entrySet()) {
                Map.Entry entry = (Map.Entry) obj;
                writer.startNode(entry.getKey().toString());
                Object val = entry.getValue();
                if (null != val) {
                    writer.setValue(val.toString());
                }
                writer.endNode();
            }

        }

        @Override
        public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {

            Map<String, String> map = new HashMap<String, String>(16);

            while (reader.hasMoreChildren()) {
                reader.moveDown();
                // nodeName aka element's name
                String key = reader.getNodeName();
                String value = reader.getValue();
                map.put(key, value);

                reader.moveUp();
            }

            return map;
        }

    }


    private static class XppDriverWithCDATA extends DomDriver {

        public XppDriverWithCDATA(String encoding, NameCoder nameCoder) {
            super(encoding, nameCoder);
        }

        @Override
        public HierarchicalStreamWriter createWriter(Writer out) {
            return new PrettyPrintWriter(out) {
                // ?????????xml????????????????????????CDATA??????
                boolean cdata = true;

                @Override
                @SuppressWarnings("rawtypes")
                public void startNode(String name, Class clazz) {
                    super.startNode(name, clazz);
                }

                @Override
                public String encodeNode(String name) {
                    return name;
                }


                @Override
                protected void writeText(QuickWriter writer, String text) {
                    if (cdata) {
                        writer.write("<![CDATA[");
                        writer.write(text);
                        writer.write("]]>");
                    } else {
                        writer.write(text);
                    }
                }
            };
        }
    }
}
