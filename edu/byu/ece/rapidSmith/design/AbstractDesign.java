package edu.byu.ece.rapidSmith.design;

import edu.byu.ece.rapidSmith.design.subsite.CellDesign;
import edu.byu.ece.rapidSmith.device.PrimitiveType;
import edu.byu.ece.rapidSmith.util.FamilyType;
import edu.byu.ece.rapidSmith.util.PartNameTools;

import java.io.Serializable;
import java.util.*;

/**
 *
 */
public abstract class AbstractDesign implements Serializable {
	private static final long serialVersionUID = 6284690406230426968L;
	/**  Keeps track of all slice primitive types, initialized statically */
	public static Set<PrimitiveType> sliceTypes;
	/**  Keeps track of all DSP48 primitive types, initialized statically */
	public static Set<PrimitiveType> dspTypes;
	/**  Keeps track of all BRAM primitive types, initialized statically */
	public static Set<PrimitiveType> bramTypes;
	/**  Keeps track of all IOB primitive types, initialized statically */
	public static Set<PrimitiveType> iobTypes;
	/**  Name of the design */
	protected String name;
	// use partName instead of device here to allow speed grade to be specified
	/**  This is the Xilinx part, package and speed grade that this design targets */
	protected String partName;

	public AbstractDesign() {
		partName = null;
		name = null;
	}

	public AbstractDesign(String designName, String partName) {
		this();
		setName(designName);
		setPartName(partName);
	}

	/**
	 * Returns the name of this design.
	 *
	 * @return the name of this design
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name of this design
	 *
	 * @param name new name for this design
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * This will return the part name with speed grade of the part this design or
	 * hard macro targets (ex: xc4vsx35ff668-10).
	 *
	 * @return he part name with package and speed grade information
	 */
	public String getPartName() {
		return this.partName;
	}

	/**
	 * Sets the name of the part used for this design.
	 * The part name should include package and speed grade.  For example
	 * xc4vfx12ff668-10 is a valid part name.
	 *
	 * @param partName name of the FPGA part.
	 */
	public void setPartName(String partName) {
		this.partName = partName;
	}

	/**
	 * Returns the all lower case exact Xilinx family name this design
	 * targets (ex: qvirtex4 instead of virtex4).
	 * DO NOT use exact family methods if it is to be used for accessing device
	 * or wire enumeration files as RapidSmith does not generate files for devices
	 * that have XDLRC compatible files.
	 *
	 * @return the exact Xilinx family name this design targets
	 */
	public String getExactFamilyName() {
		return PartNameTools.getExactFamilyNameFromPart(partName);
	}

	/**
	 * Returns the all lower case base family name this design targets.
	 * This ensures compatibility with all RapidSmith files. For differentiating
	 * family names (qvirtex4 rather than virtex4) use getExactFamilyName().
	 *
	 * @return the base family name of the part this design targets
	 */
	public String getFamilyName() {
		return PartNameTools.getFamilyNameFromPart(partName);
	}

	/**
	 * Returns the all lower case exact Xilinx family type this design targets
	 * (ex: qvirtex4 instead of virtex4).
	 * DO NOT use exact family methods if it is to be used for accessing device or
	 * wire enumeration files as RapidSmith does not generate files for devices that
	 * have XDLRC compatible files.
	 *
	 * @return the exact Xilinx family type this design targets
	 */
	public FamilyType getExactFamilyType() {
		return PartNameTools.getExactFamilyTypeFromPart(partName);
	}

	/**
	 * Returns the base family type this design targets.
	 * This ensures compatibility with all RapidSmith files. For differentiating
	 * family types (qvirtex4 rather than virtex4) use getExactFamilyType().
	 *
	 * @return the base family type of the part this design targets
	 */
	public FamilyType getFamilyType() {
		return PartNameTools.getFamilyTypeFromPart(partName);
	}

	static {
		sliceTypes = new HashSet<>();
		sliceTypes.add(PrimitiveType.SLICE);
		sliceTypes.add(PrimitiveType.SLICEL);
		sliceTypes.add(PrimitiveType.SLICEM);
		sliceTypes.add(PrimitiveType.SLICEX);

		dspTypes = new HashSet<>();
		dspTypes.add(PrimitiveType.DSP48);
		dspTypes.add(PrimitiveType.DSP48A);
		dspTypes.add(PrimitiveType.DSP48A1);
		dspTypes.add(PrimitiveType.DSP48E);
		dspTypes.add(PrimitiveType.DSP48E1);
		dspTypes.add(PrimitiveType.MULT18X18);
		dspTypes.add(PrimitiveType.MULT18X18SIO);

		bramTypes = new HashSet<>();
		bramTypes.add(PrimitiveType.BLOCKRAM);
		bramTypes.add(PrimitiveType.FIFO16);
		bramTypes.add(PrimitiveType.FIFO18E1);
		bramTypes.add(PrimitiveType.FIFO36_72_EXP);
		bramTypes.add(PrimitiveType.FIFO36_EXP);
		bramTypes.add(PrimitiveType.FIFO36E1);
		bramTypes.add(PrimitiveType.RAMB16);
		bramTypes.add(PrimitiveType.RAMB16BWE);
		bramTypes.add(PrimitiveType.RAMB16BWER);
		bramTypes.add(PrimitiveType.RAMB18E1);
		bramTypes.add(PrimitiveType.RAMB18X2);
		bramTypes.add(PrimitiveType.RAMB18X2SDP);
		bramTypes.add(PrimitiveType.RAMB36_EXP);
		bramTypes.add(PrimitiveType.RAMB36E1);
		bramTypes.add(PrimitiveType.RAMB36SDP_EXP);
		bramTypes.add(PrimitiveType.RAMB8BWER);
		bramTypes.add(PrimitiveType.RAMBFIFO18);
		bramTypes.add(PrimitiveType.RAMBFIFO18_36);
		bramTypes.add(PrimitiveType.RAMBFIFO36);
		bramTypes.add(PrimitiveType.RAMBFIFO36E1);

		iobTypes = new HashSet<>();
		iobTypes.add(PrimitiveType.IOB);
		iobTypes.add(PrimitiveType.IOB18);
		iobTypes.add(PrimitiveType.IOB18M);
		iobTypes.add(PrimitiveType.IOB18S);
		iobTypes.add(PrimitiveType.IOB33);
		iobTypes.add(PrimitiveType.IOB33M);
		iobTypes.add(PrimitiveType.IOB33S);
		iobTypes.add(PrimitiveType.IOB_USB);
		iobTypes.add(PrimitiveType.IOBLR);
		iobTypes.add(PrimitiveType.IOBM);
		iobTypes.add(PrimitiveType.IOBS);
		iobTypes.add(PrimitiveType.LOWCAPIOB);
	}

}