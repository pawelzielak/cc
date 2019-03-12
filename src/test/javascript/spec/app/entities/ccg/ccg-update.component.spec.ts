/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { CcTestModule } from '../../../test.module';
import { CCGUpdateComponent } from 'app/entities/ccg/ccg-update.component';
import { CCGService } from 'app/entities/ccg/ccg.service';
import { CCG } from 'app/shared/model/ccg.model';

describe('Component Tests', () => {
    describe('CCG Management Update Component', () => {
        let comp: CCGUpdateComponent;
        let fixture: ComponentFixture<CCGUpdateComponent>;
        let service: CCGService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [CcTestModule],
                declarations: [CCGUpdateComponent]
            })
                .overrideTemplate(CCGUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CCGUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CCGService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new CCG(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cCG = entity;
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
                    const entity = new CCG();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.cCG = entity;
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
