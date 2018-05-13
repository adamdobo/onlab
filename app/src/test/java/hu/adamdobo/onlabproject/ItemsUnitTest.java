package hu.adamdobo.onlabproject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import hu.adamdobo.onlabproject.items.ItemsInteractor;
import hu.adamdobo.onlabproject.items.ItemsPresenter;
import hu.adamdobo.onlabproject.items.ItemsPresenterImpl;
import hu.adamdobo.onlabproject.items.ItemsView;
import hu.adamdobo.onlabproject.model.Item;

import static junit.framework.Assert.assertNotNull;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Ádám on 5/7/2018.
 */

@RunWith(MockitoJUnitRunner.class)
public class ItemsUnitTest {

    @Mock
    ItemsInteractor itemsInteractor;

    @Mock
    ItemsView itemsView;

    ItemsPresenter presenter;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        when(itemsInteractor.getAddedItem()).thenReturn(new Item());
        when(itemsInteractor.getChangedItem()).thenReturn(new Item());
        when(itemsInteractor.getDeletedItem()).thenReturn(new Item());

        presenter = new ItemsPresenterImpl(itemsView, itemsInteractor);
    }

    @Test
    public void testGetAddedItem() {
        Item item = presenter.getAddedItem();
        verify(itemsInteractor).getAddedItem();
        assertNotNull(item);
    }

    @Test
    public void testGetChangedItem(){
        Item item = presenter.getChangedItem();
        verify(itemsInteractor).getChangedItem();
        assertNotNull(item);
    }

    @Test
    public void testGetDeletedItem(){
        Item item = presenter.getDeletedItem();
        verify(itemsInteractor).getDeletedItem();
        assertNotNull(item);
    }

    @Test
    public void testSaveItemWithPicture(){
        Item item = new Item();
        byte[] bytes = new byte[2];
        presenter.saveItemWithPicture(item, bytes);
        verify(itemsView).showProgress();
        verify(itemsInteractor).saveItemWithPicture(item, bytes);
    }

    @Test
    public void testOnItemAdded(){
        presenter.onItemAdded();
        verify(itemsView).onItemAdded();
    }

    @Test
    public void testOnItemChanged(){
        presenter.onItemChanged();
        verify(itemsView).onItemChanged();
    }

    @Test
    public void testOnItemDeleted(){
        presenter.onItemDeleted();
        verify(itemsView).onItemDeleted();
    }

    @Test
    public void testOnItemSaveSuccess(){
        presenter.onItemSaveSuccess();
        verify(itemsView).hideProgress();
    }

    @Test
    public void testOnDestroy(){
        presenter.onDestroy();
        presenter.onItemAdded();
        verify(itemsView, never()).onItemAdded();
    }

}
