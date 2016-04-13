/*
 * Copyright 2015, 2016 Tagir Valeev
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package one.util.huntbugs.warning;

import one.util.huntbugs.warning.WarningAnnotation.MemberInfo;

/**
 * @author lan
 *
 */
public class Formatter {
    private final Messages msgs;
    
    public Formatter() {
        this(Messages.load());
    }

    public Formatter(Messages msgs) {
        this.msgs = msgs;
    }
    
    public String getTitle(Warning warning) {
        return msgs.getMessagesForType(warning.getType()).getTitle();
    }
    
    public String getDescription(Warning warning) {
        return format(msgs.getMessagesForType(warning.getType()).getDescription(), warning);
    }

    public String getLongDescription(Warning warning) {
        return format(msgs.getMessagesForType(warning.getType()).getLongDescription(), warning);
    }
    
    private String format(String description, Warning warning) {
        String[] fields = description.split("\\$", -1);
        if(fields.length == 1)
            return description;
        StringBuilder result = new StringBuilder(fields[0]);
        for(int i=1; i<fields.length; i++) {
            if(i % 2 == 0) {
                result.append(fields[i]);
            } else {
                WarningAnnotation<?> anno = warning.getAnnotation(fields[i]);
                if(anno == null) {
                    result.append('(').append(fields[i]).append(')');
                } else {
                    result.append(formatValue(anno.getValue()));
                }
            }
        }
        return result.toString();
    }

    public String formatValue(Object value) {
        if(value instanceof MemberInfo) {
            MemberInfo mi = (MemberInfo)value;
            String type = mi.typeName;
            int pos = type.lastIndexOf('/');
            if(pos > -1)
                type = type.substring(pos+1).replace('$', '.');
            return type+"."+mi.name+(mi.signature.startsWith("(")?"()":"");
        }
        if(value instanceof Double) {
            double val = (Double)value;
            if(Double.isNaN(val))
                return "Double.NaN";
            if(val == Double.POSITIVE_INFINITY) 
                return "Double.POSITIVE_INFINITY";
            if(val == Double.NEGATIVE_INFINITY)
                return "Double.NEGATIVE_INFINITY";
            if(val == Double.MIN_VALUE)
                return "Double.MIN_VALUE";
            if(val == Double.MAX_VALUE)
                return "Double.MAX_VALUE";
            if(val == -Double.MIN_VALUE)
                return "-Double.MIN_VALUE";
            if(val == -Double.MAX_VALUE)
                return "-Double.MAX_VALUE";
            return Double.toString(val);
        }
        if(value instanceof Float) {
            float val = (Float)value;
            if(Float.isNaN(val))
                return "Float.NaN";
            if(val == Float.POSITIVE_INFINITY) 
                return "Float.POSITIVE_INFINITY";
            if(val == Float.NEGATIVE_INFINITY)
                return "Float.NEGATIVE_INFINITY";
            if(val == Float.MIN_VALUE)
                return "Float.MIN_VALUE";
            if(val == Float.MAX_VALUE)
                return "Float.MAX_VALUE";
            if(val == -Float.MIN_VALUE)
                return "-Float.MIN_VALUE";
            if(val == -Float.MAX_VALUE)
                return "-Float.MAX_VALUE";
            return Float.toString(val);
        }
        return String.valueOf(value);
    }
}