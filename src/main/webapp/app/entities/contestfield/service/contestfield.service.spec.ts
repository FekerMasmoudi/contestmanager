import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { IContestfield } from '../contestfield.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../contestfield.test-samples';

import { ContestfieldService } from './contestfield.service';

const requireRestSample: IContestfield = {
  ...sampleWithRequiredData,
};

describe('Contestfield Service', () => {
  let service: ContestfieldService;
  let httpMock: HttpTestingController;
  let expectedResult: IContestfield | IContestfield[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(ContestfieldService);
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

    it('should create a Contestfield', () => {
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
      const contestfield = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(contestfield).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Contestfield', () => {
      const contestfield = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(contestfield).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Contestfield', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Contestfield', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Contestfield', () => {
      const expected = true;

      service.delete('ABC').subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContestfieldToCollectionIfMissing', () => {
      it('should add a Contestfield to an empty array', () => {
        const contestfield: IContestfield = sampleWithRequiredData;
        expectedResult = service.addContestfieldToCollectionIfMissing([], contestfield);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contestfield);
      });

      it('should not add a Contestfield to an array that contains it', () => {
        const contestfield: IContestfield = sampleWithRequiredData;
        const contestfieldCollection: IContestfield[] = [
          {
            ...contestfield,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContestfieldToCollectionIfMissing(contestfieldCollection, contestfield);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Contestfield to an array that doesn't contain it", () => {
        const contestfield: IContestfield = sampleWithRequiredData;
        const contestfieldCollection: IContestfield[] = [sampleWithPartialData];
        expectedResult = service.addContestfieldToCollectionIfMissing(contestfieldCollection, contestfield);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contestfield);
      });

      it('should add only unique Contestfield to an array', () => {
        const contestfieldArray: IContestfield[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const contestfieldCollection: IContestfield[] = [sampleWithRequiredData];
        expectedResult = service.addContestfieldToCollectionIfMissing(contestfieldCollection, ...contestfieldArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contestfield: IContestfield = sampleWithRequiredData;
        const contestfield2: IContestfield = sampleWithPartialData;
        expectedResult = service.addContestfieldToCollectionIfMissing([], contestfield, contestfield2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contestfield);
        expect(expectedResult).toContain(contestfield2);
      });

      it('should accept null and undefined values', () => {
        const contestfield: IContestfield = sampleWithRequiredData;
        expectedResult = service.addContestfieldToCollectionIfMissing([], null, contestfield, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contestfield);
      });

      it('should return initial array if no Contestfield is added', () => {
        const contestfieldCollection: IContestfield[] = [sampleWithRequiredData];
        expectedResult = service.addContestfieldToCollectionIfMissing(contestfieldCollection, undefined, null);
        expect(expectedResult).toEqual(contestfieldCollection);
      });
    });

    describe('compareContestfield', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContestfield(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = null;

        const compareResult1 = service.compareContestfield(entity1, entity2);
        const compareResult2 = service.compareContestfield(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'CBA' };

        const compareResult1 = service.compareContestfield(entity1, entity2);
        const compareResult2 = service.compareContestfield(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 'ABC' };
        const entity2 = { id: 'ABC' };

        const compareResult1 = service.compareContestfield(entity1, entity2);
        const compareResult2 = service.compareContestfield(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
