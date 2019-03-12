/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CcTestModule } from '../../../test.module';
import { AliasUpdateComponent } from 'app/entities/alias/alias-update.component';
import { AliasService } from 'app/entities/alias/alias.service';
import { Alias } from 'app/shared/model/alias.model';

describe('Component Tests', () => {
    describe('Alias Management Update Component', () => {
        let comp: AliasUpdateComponent;
        let fixture: ComponentFixture<AliasUpdateComponent>;
        let service: AliasService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CcTestModule],
                declarations: [AliasUpdateComponent]
            })
                .overrideTemplate(AliasUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AliasUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AliasService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Alias(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.alias = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Alias();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.alias = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
