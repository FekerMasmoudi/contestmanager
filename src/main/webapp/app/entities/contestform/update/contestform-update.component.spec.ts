import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ContestformFormService } from './contestform-form.service';
import { ContestformService } from '../service/contestform.service';
import { IContestform } from '../contestform.model';
import { IContest } from 'app/entities/contest/contest.model';
import { ContestService } from 'app/entities/contest/service/contest.service';

import { IUser } from 'app/entities/user/user.model';
import { UserService } from 'app/entities/user/user.service';

import { ContestformUpdateComponent } from './contestform-update.component';

describe('Contestform Management Update Component', () => {
  let comp: ContestformUpdateComponent;
  let fixture: ComponentFixture<ContestformUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contestformFormService: ContestformFormService;
  let contestformService: ContestformService;
  let contestService: ContestService;
  let userService: UserService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ContestformUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(ContestformUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContestformUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contestformFormService = TestBed.inject(ContestformFormService);
    contestformService = TestBed.inject(ContestformService);
    contestService = TestBed.inject(ContestService);
    userService = TestBed.inject(UserService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Contest query and add missing value', () => {
      const contestform: IContestform = { id: 'CBA' };
      const contest: IContest = { id: '6ce294b9-c409-4a9e-bce7-3f6cbeb97dc2' };
      contestform.contest = contest;

      const contestCollection: IContest[] = [{ id: '64375839-9475-42b2-9910-05ec7a8ab0ce' }];
      jest.spyOn(contestService, 'query').mockReturnValue(of(new HttpResponse({ body: contestCollection })));
      const additionalContests = [contest];
      const expectedCollection: IContest[] = [...additionalContests, ...contestCollection];
      jest.spyOn(contestService, 'addContestToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contestform });
      comp.ngOnInit();

      expect(contestService.query).toHaveBeenCalled();
      expect(contestService.addContestToCollectionIfMissing).toHaveBeenCalledWith(
        contestCollection,
        ...additionalContests.map(expect.objectContaining)
      );
      expect(comp.contestsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call User query and add missing value', () => {
      const contestform: IContestform = { id: 'CBA' };
      const user: IUser = { id: 'c201fb9a-6349-4037-a8e1-378cbd59cbf6' };
      contestform.user = user;

      const userCollection: IUser[] = [{ id: 'f4b9a795-a93d-4d93-b3a9-ac893b30ce06' }];
      jest.spyOn(userService, 'query').mockReturnValue(of(new HttpResponse({ body: userCollection })));
      const additionalUsers = [user];
      const expectedCollection: IUser[] = [...additionalUsers, ...userCollection];
      jest.spyOn(userService, 'addUserToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ contestform });
      comp.ngOnInit();

      expect(userService.query).toHaveBeenCalled();
      expect(userService.addUserToCollectionIfMissing).toHaveBeenCalledWith(
        userCollection,
        ...additionalUsers.map(expect.objectContaining)
      );
      expect(comp.usersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const contestform: IContestform = { id: 'CBA' };
      const contest: IContest = { id: '760901a7-57ce-4ffd-8cb5-88da85398e97' };
      contestform.contest = contest;
      const user: IUser = { id: '76b8453f-e0e7-40ad-987a-9d65a7b1cb81' };
      contestform.user = user;

      activatedRoute.data = of({ contestform });
      comp.ngOnInit();

      expect(comp.contestsSharedCollection).toContain(contest);
      expect(comp.usersSharedCollection).toContain(user);
      expect(comp.contestform).toEqual(contestform);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContestform>>();
      const contestform = { id: 'ABC' };
      jest.spyOn(contestformFormService, 'getContestform').mockReturnValue(contestform);
      jest.spyOn(contestformService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contestform });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contestform }));
      saveSubject.complete();

      // THEN
      expect(contestformFormService.getContestform).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contestformService.update).toHaveBeenCalledWith(expect.objectContaining(contestform));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContestform>>();
      const contestform = { id: 'ABC' };
      jest.spyOn(contestformFormService, 'getContestform').mockReturnValue({ id: null });
      jest.spyOn(contestformService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contestform: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contestform }));
      saveSubject.complete();

      // THEN
      expect(contestformFormService.getContestform).toHaveBeenCalled();
      expect(contestformService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContestform>>();
      const contestform = { id: 'ABC' };
      jest.spyOn(contestformService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contestform });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contestformService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareContest', () => {
      it('Should forward to contestService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(contestService, 'compareContest');
        comp.compareContest(entity, entity2);
        expect(contestService.compareContest).toHaveBeenCalledWith(entity, entity2);
      });
    });

    describe('compareUser', () => {
      it('Should forward to userService', () => {
        const entity = { id: 'ABC' };
        const entity2 = { id: 'CBA' };
        jest.spyOn(userService, 'compareUser');
        comp.compareUser(entity, entity2);
        expect(userService.compareUser).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
