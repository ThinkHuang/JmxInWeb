package com.senatry.jmxInWeb.models;

import java.util.LinkedList;
import java.util.List;

import javax.management.JMException;
import javax.management.MBeanAttributeInfo;
import javax.management.MBeanInfo;
import javax.management.MBeanOperationInfo;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * <pre>
 * 对MBeanInfo的包装
 * </pre>
 * 
 * @author 梁韦江 2015年9月11日
 */
public class MBeanVo {
	private final MBeanInfo info;
	private final ObjectName targetName;
	private final List<MBeanAttrVo> attrs = new LinkedList<MBeanAttrVo>();
	private final List<MBeanOptVo> opts = new LinkedList<MBeanOptVo>();

	private final Class<?> clazz;

	public MBeanVo(ObjectName targetName, MBeanInfo mBeanInfo) {
		super();
		this.targetName = targetName;
		this.info = mBeanInfo;

		Class<?> temp = null;
		try {
			temp = Class.forName(info.getClassName());
		} catch (ClassNotFoundException e) {
		}
		this.clazz = temp;

		MBeanAttributeInfo[] attrArray = mBeanInfo.getAttributes();
		if (attrArray != null) {
			for (MBeanAttributeInfo mBeanAttributeInfo : attrArray) {
				this.attrs.add(new MBeanAttrVo(mBeanAttributeInfo));
			}
		}

		MBeanOperationInfo[] operations = mBeanInfo.getOperations();
		if (operations != null) {
			for (MBeanOperationInfo mBeanOperationInfo : operations) {
				if (isOperationRole(mBeanOperationInfo)) {
					// 只有role = "operation"才需要加进来，role是setter或者getter的，都是属性的
					this.opts.add(new MBeanOptVo(mBeanOperationInfo));
				}
			}
		}
	}

	public List<MBeanAttrVo> getAttrs() {
		return attrs;
	}

	public String getClassName() {
		return this.info.getClassName();
	}

	public String getDesc() {
		return this.info.getDescription();
	}

	public String getObjectName() {
		return this.targetName.getCanonicalName();
	}

	public String getSimpleName() {
		return this.clazz.getSimpleName();
	}

	public List<MBeanOptVo> getOpts() {
		return opts;
	}

	public void setValueFromMBeanServer(MBeanServer server) throws JMException {
		for (MBeanAttrVo attrVo : attrs) {
			attrVo.setValueFromMBeanServer(targetName, server);
		}
	}

	/**
	 * 判断一个操作的role是否是operation
	 * 
	 * @param info
	 * @return
	 */
	private boolean isOperationRole(MBeanOperationInfo info) {
		Object value = info.getDescriptor().getFieldValue("role");
		return value != null && "operation".equals(value.toString());
	}
}