package com.eclipserunner.utils;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class SelectionUtilsTest {

	interface IDummy {
		public String getName();
	}
	
	class Dummy implements IDummy {
		private String name;
		public Dummy(String name) {
			this.name = name;
		}
		public String getName() {
			return name;
		}
	}
	
	class ExtDummy extends Dummy {
		public ExtDummy(String name) {
			super(name);
		}
	}
	
	@Mock
	private IStructuredSelection structuredSelectionMock;

	@Mock
	private ISelection selectionMock;
	
	@Before
	public void initMocks() {
        MockitoAnnotations.initMocks(this);
	}
	
	// getFirstByType tests
	
	@Test
	public void testGetFirstByInterfaceType() throws Exception {
        List<Object> list = Arrays.asList("dummy string", 1L, new Dummy("1"), 2, new Dummy("2"), 3L);
        when(structuredSelectionMock.iterator()).thenReturn(list.iterator());
		
		IDummy dummy = SelectionUtils.getFirstSelectedItemByType(structuredSelectionMock, IDummy.class);
		
		assertEquals(dummy.getName(), "1");
		
		verify(structuredSelectionMock).iterator();
	}
	
	@Test
	public void testGetFirstByInterfaceTypeWithSubclass() throws Exception {
		List<Object> list = Arrays.asList("dummy string", 1L, new ExtDummy("1"), 2, new Dummy("2"), 3L);
        when(structuredSelectionMock.iterator()).thenReturn(list.iterator());
		
		IDummy dummy = SelectionUtils.getFirstSelectedItemByType(structuredSelectionMock, IDummy.class);
		
		assertEquals(dummy.getName(), "1");
		
		verify(structuredSelectionMock).iterator();
	}
	
	@Test
	public void testGetFirstByClassType() throws Exception {
        List<Object> list = Arrays.asList("dummy string", 1L, new ExtDummy("1"), 2, new Dummy("2"), 3L);
        when(structuredSelectionMock.iterator()).thenReturn(list.iterator());
		
		IDummy dummy = SelectionUtils.getFirstSelectedItemByType(structuredSelectionMock, Dummy.class);
		
		assertEquals(dummy.getName(), "1");
		
		verify(structuredSelectionMock).iterator();
	}
	
	@Test
	public void testGetFirstByTypeWithISelection() throws Exception {
		IDummy dummy = SelectionUtils.getFirstSelectedItemByType(selectionMock, Dummy.class);
		assertNull(dummy);
	}
	
	// getAllByType tests
	
	@Test
	public void testGetAllBy() throws Exception {
		IDummy dummy1 = new ExtDummy("1");
		IDummy dummy2 = new Dummy("2");
		
        List<Object> list = Arrays.asList("dummy string", 1L, dummy1, 2, dummy2, 3L);
        when(structuredSelectionMock.iterator()).thenReturn(list.iterator());
		
		List<Dummy> dummyList = SelectionUtils.getAllSelectedItemsByType(structuredSelectionMock, Dummy.class);
		
		assertEquals(dummyList.size(), 2);
		assertTrue(dummyList.contains(dummy1));
		assertTrue(dummyList.contains(dummy2));
		
		verify(structuredSelectionMock).iterator();
	}
	
	@Test
	public void testGetAllByTypeWithISelection() throws Exception {
		List<Dummy> dummyList = SelectionUtils.getAllSelectedItemsByType(selectionMock, Dummy.class);
		assertEquals(dummyList.size(), 0);
	}
}
