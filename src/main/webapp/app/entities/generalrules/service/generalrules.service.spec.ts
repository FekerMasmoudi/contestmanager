import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IGeneralrules } from '../generalrules.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../generalrules.test-samples';

import { GeneralrulesService, RestGeneralrules } from './generalrules.service';

const requireRestSample: RestGeneralrules = {
  ...sampleWithRequiredData,
  effectdate: sampleWithRequiredData.effectdate?.format(DATE_FORMAT),
};

describe('Generalrules Service', () => {
  let service: GeneralrulesService;
  let httpMock: HttpTestingController;
  let expectedResult: IGeneralrules | IGeneralrules[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(GeneralrulesService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find('ABC').subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Generalrules', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const generalrules = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(generalrules).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Generalrules', () => {
      const generalrules = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(generalrules).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Generalrules', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Generalrules', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Generalrules', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addGeneralrulesToCollectionIfMissing', () => {
      it('should add a Generalrules to an empty array', () => {
        const generalrules: IGeneralrules = sampleWithRequiredData;
        expectedResult = service.addGeneralrulesToCollectionIfMissing([], generalrules);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(generalrules);
      });

      it('should not add a Generalrules to an array that contains it', () => {
        const generalrules: IGeneralrules = sampleWithRequiredData;
        const generalrulesCollection: IGeneralrules[] = [
          {
            ...generalrules,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addGeneralrulesToCollectionIfMissing(generalrulesCollection, generalrules);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Generalrules to an array that doesn't contain it", () => {
        const generalrules: IGeneralrules = sampleWithRequiredData;
        const generalrulesCollection: IGeneralrules[] = [sampleWithPartialData];
        expectedResult = service.addGeneralrulesToCollectionIfMissing(generalrulesCollection, generalrules);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(generalrules);
      });

      it('should add only unique Generalrules to an array', () => {
        const generalrulesArray: IGeneralrules[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const generalrulesCollection: IGeneralrules[] = [sampleWithRequiredData];
        expectedResult = service.addGeneralrulesToCollectionIfMissing(generalrulesCollection, ...generalrulesArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const generalrules: IGeneralrules = sampleWithRequiredData;
        const generalrules2: IGeneralrules = sampleWithPartialData;
        expectedResult = service.addGeneralrulesToCollectionIfMissing([], generalrules, generalrules2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(generalrules);
        expect(expectedResult).toContain(generalrules2);
      });

      it('should accept null and undefined values', () => {
        const generalrules: IGeneralrules = sampleWithRequiredData;
        expectedResult = service.addGeneralrulesToCollectionIfMissing([], null, generalrules, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(generalrules);
      });

      it('should return initial array if no Generalrules is added', () => {
        const generalrulesCollection: IGeneralrules[] = [sampleWithRequiredData];
        expectedResult = service.addGeneralrulesToCollectionIfMissing(generalrulesCollection, undefined, null);
        expect(expectedResult).toEqual(generalrulesCollection);
      });
    });

    describe('compareGeneralrules', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareGeneralrules(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareGeneralrules(entity1, entity2);
        const compareResult2 = service.compareGeneralrules(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareGeneralrules(entity1, entity2);
        const compareResult2 = service.compareGeneralrules(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareGeneralrules(entity1, entity2);
        const compareResult2 = service.compareGeneralrules(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
